package bg.tuvarna.sit.project.ps.internshipmanagement.service;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.admin.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.exception.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class StudentAccountService {
    private final UserRepository users;
    private final StudentProfileRepository profiles;
    private final PasswordEncoder encoder;
    private final SecureRandom random = new SecureRandom();
    public StudentAccountService(UserRepository users, StudentProfileRepository profiles, PasswordEncoder encoder) {
        this.users=users; this.profiles=profiles; this.encoder=encoder;
    }
    private String temporaryPassword() {
        byte[] bytes=new byte[18]; random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
    @Transactional
    public StudentCredentials create(CreateStudentRequest request) {
        String email="s" + request.facultyNumber() + "@students.example";
        if (users.existsByEmail(email) || profiles.existsByFacultyNumber(request.facultyNumber()))
            throw new DuplicateResourceException("A student with this faculty number already exists");
        String password=temporaryPassword();
        User user=users.save(User.builder().firstName(request.firstName().trim()).lastName(request.lastName().trim())
            .email(email).password(encoder.encode(password)).role(Role.STUDENT).authProvider(AuthProvider.UNIVERSITY)
            .externalId(request.facultyNumber()).enabled(true).passwordChangeRequired(true).build());
        profiles.save(StudentProfile.builder().user(user).facultyNumber(request.facultyNumber())
            .specialty(request.specialty().trim()).course(request.course()).skills("").build());
        return new StudentCredentials(user.getId(), email, password);
    }
    @Transactional
    public StudentCredentials reset(Long id) {
        User user=users.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        if (user.getRole()!=Role.STUDENT) throw new BadRequestException("Only student passwords can be reset here");
        String password=temporaryPassword();
        user.setPassword(encoder.encode(password));
        user.setPasswordChangeRequired(true);
        user.setTokenVersion(user.getTokenVersion()+1);
        users.save(user);
        return new StudentCredentials(user.getId(),user.getEmail(),password);
    }
}
