package bg.tuvarna.sit.project.ps.internshipmanagement.service;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.companyregistration.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.exception.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.mapper.CompanyRegistrationMapper;
import bg.tuvarna.sit.project.ps.internshipmanagement.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

@Service
public class CompanyRegistrationService {
    private final CompanyRegistrationRequestRepository requests;
    private final UserRepository users;
    private final CompanyRepository companies;
    private final PasswordEncoder encoder;
    private final CompanyRegistrationMapper mapper;
    private final SecureRandom random = new SecureRandom();
    public CompanyRegistrationService(CompanyRegistrationRequestRepository r, UserRepository u,
            CompanyRepository c, PasswordEncoder e, CompanyRegistrationMapper m) {
        requests=r; users=u; companies=c; encoder=e; mapper=m;
    }
    @Transactional
    public RegistrationReceipt create(CompanyRegistrationCreateRequest input) {
        input.setRepresentativeEmail(input.getRepresentativeEmail().trim().toLowerCase(Locale.ROOT));
        input.setContactEmail(input.getContactEmail().trim().toLowerCase(Locale.ROOT));
        if (users.existsByEmail(input.getRepresentativeEmail()) || companies.existsByContactEmail(input.getContactEmail()))
            throw new DuplicateResourceException("An account already uses these details. Sign in or contact the administrator.");
        LocalDateTime now=LocalDateTime.now();
        for (var previous : requests.findMatching(input.getRepresentativeEmail(), input.getContactEmail())) {
            if (previous.getStatus()!=CompanyRegistrationStatus.REJECTED)
                throw new DuplicateResourceException("A registration already exists. Use your saved status link or sign in.");
            if (previous.getReviewedAt()==null || now.isBefore(previous.getReviewedAt().plusHours(24)))
                throw new BadRequestException("You can submit again 24 hours after the rejection. Check your status page for the time.");
        }
        if (input.getPassword().getBytes(StandardCharsets.UTF_8).length>72)
            throw new BadRequestException("Password must fit within 72 UTF-8 bytes");
        byte[] bytes=new byte[32]; random.nextBytes(bytes);
        String token=Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        var entity=mapper.toEntity(input,encoder.encode(input.getPassword()));
        entity.setStatusTokenHash(hash(token));
        requests.saveAndFlush(entity);
        return new RegistrationReceipt(token,publicStatus(entity));
    }
    @Transactional(readOnly=true)
    public RegistrationStatusResponse status(String token) {
        if (token==null || !token.matches("[A-Za-z0-9_-]{43}")) throw new ResourceNotFoundException("Status link not found");
        return publicStatus(requests.findByStatusTokenHash(hash(token))
                .orElseThrow(() -> new ResourceNotFoundException("Status link not found")));
    }
    private String hash(String token) {
        try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(token.getBytes(StandardCharsets.UTF_8))); }
        catch (NoSuchAlgorithmException exception) { throw new IllegalStateException(exception); }
    }
    private RegistrationStatusResponse publicStatus(CompanyRegistrationRequest entity) {
        LocalDateTime retry=entity.getStatus()==CompanyRegistrationStatus.REJECTED && entity.getReviewedAt()!=null
                ? entity.getReviewedAt().plusHours(24) : null;
        return new RegistrationStatusResponse(entity.getCompanyName(),entity.getStatus(),entity.getAdminComment(),
                entity.getRequestedAt(),entity.getReviewedAt(),retry);
    }
    @Transactional(readOnly=true)
    public List<CompanyRegistrationResponse> pending() {
        return requests.findByStatus(CompanyRegistrationStatus.PENDING).stream().map(x->mapper.toResponse(x,"Pending review")).toList();
    }
    @Transactional(readOnly=true)
    public List<CompanyRegistrationResponse> all() {
        return requests.findAll().stream().sorted(Comparator.comparing(CompanyRegistrationRequest::getRequestedAt).reversed())
                .map(x->mapper.toResponse(x,"Company registration request")).toList();
    }
    @Transactional(readOnly=true)
    public CompanyRegistrationResponse get(Long id) {
        return mapper.toResponse(requests.findById(id).orElseThrow(()->new ResourceNotFoundException("Registration request not found")),"Company registration request");
    }
    private CompanyRegistrationRequest forReview(Long id) {
        var request=requests.findForReview(id).orElseThrow(()->new ResourceNotFoundException("Registration request not found"));
        if (request.getStatus()!=CompanyRegistrationStatus.PENDING) throw new BadRequestException("This request has already been reviewed");
        return request;
    }
    @Transactional
    public CompanyRegistrationResponse approve(Long id,CompanyRegistrationReviewRequest review) {
        var request=forReview(id);
        if (users.existsByEmail(request.getRepresentativeEmail()) || companies.existsByContactEmail(request.getContactEmail()))
            throw new DuplicateResourceException("An account already uses these details");
        User user=users.save(User.builder().firstName(request.getRepresentativeFirstName()).lastName(request.getRepresentativeLastName())
                .email(request.getRepresentativeEmail()).password(request.getPasswordHash()).role(Role.COMPANY).authProvider(AuthProvider.LOCAL).enabled(true).build());
        Company company=companies.save(Company.builder().name(request.getCompanyName()).description(request.getDescription()).website(request.getWebsite())
                .contactEmail(request.getContactEmail()).city(request.getCity()).user(user).build());
        request.setCreatedCompany(company);
        finishReview(request,CompanyRegistrationStatus.APPROVED,review.getAdminComment());
        return mapper.toResponse(request,"Company approved successfully");
    }
    @Transactional
    public CompanyRegistrationResponse reject(Long id,CompanyRegistrationReviewRequest review) {
        if (review.getAdminComment()==null || review.getAdminComment().isBlank()) throw new BadRequestException("Please provide a reason for rejection");
        var request=forReview(id);
        finishReview(request,CompanyRegistrationStatus.REJECTED,review.getAdminComment());
        return mapper.toResponse(request,"Company registration rejected");
    }
    private void finishReview(CompanyRegistrationRequest request,CompanyRegistrationStatus status,String comment) {
        request.setStatus(status); request.setReviewedAt(LocalDateTime.now());
        request.setAdminComment(comment==null?null:comment.trim()); requests.save(request);
    }
}
