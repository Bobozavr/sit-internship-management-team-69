package bg.tuvarna.sit.project.ps.internshipmanagement;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.companyregistration.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.exception.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.mapper.CompanyRegistrationMapper;
import bg.tuvarna.sit.project.ps.internshipmanagement.repository.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.service.CompanyRegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyRegistrationServiceTest {
    CompanyRegistrationRequestRepository requests=mock(CompanyRegistrationRequestRepository.class);
    UserRepository users=mock(UserRepository.class);
    CompanyRepository companies=mock(CompanyRepository.class);
    PasswordEncoder encoder=mock(PasswordEncoder.class);
    CompanyRegistrationService service=new CompanyRegistrationService(requests,users,companies,encoder,new CompanyRegistrationMapper());
    CompanyRegistrationCreateRequest input() {
        return CompanyRegistrationCreateRequest.builder().companyName("Test company").description("Description").city("Varna")
                .contactEmail("contact@example.test").representativeEmail("user@example.test").representativeFirstName("Test")
                .representativeLastName("User").password("password123").build();
    }
    void setupSave() {
        when(encoder.encode(anyString())).thenReturn("hashed-password");
        when(requests.saveAndFlush(any())).thenAnswer(invocation -> {
            CompanyRegistrationRequest entity=invocation.getArgument(0);
            entity.setId(1L); entity.setDefaultValuesBeforeInsert(); return entity;
        });
    }
    @Test void createsPrivateReceiptAndStoresOnlyHash() {
        setupSave(); var receipt=service.create(input());
        assertEquals(43,receipt.statusToken().length());
        assertEquals(CompanyRegistrationStatus.PENDING,receipt.registration().status());
        var captor=org.mockito.ArgumentCaptor.forClass(CompanyRegistrationRequest.class);
        verify(requests).saveAndFlush(captor.capture());
        assertEquals(64,captor.getValue().getStatusTokenHash().length());
        assertNotEquals(receipt.statusToken(),captor.getValue().getStatusTokenHash());
        assertEquals("hashed-password",captor.getValue().getPasswordHash());
        verify(users,never()).save(any()); verify(companies,never()).save(any());
    }
    @Test void pendingAndRecentRejectionPreventResubmission() {
        var previous=CompanyRegistrationRequest.builder().status(CompanyRegistrationStatus.PENDING).build();
        when(requests.findMatching(anyString(),anyString())).thenReturn(List.of(previous));
        assertThrows(DuplicateResourceException.class,()->service.create(input()));
        previous.setStatus(CompanyRegistrationStatus.REJECTED);previous.setReviewedAt(LocalDateTime.now().minusHours(23));
        assertThrows(BadRequestException.class,()->service.create(input()));
        verify(requests,never()).saveAndFlush(any());
    }
    @Test void rejectionOlderThan24HoursAllowsNewRequest() {
        setupSave(); var previous=CompanyRegistrationRequest.builder().status(CompanyRegistrationStatus.REJECTED).reviewedAt(LocalDateTime.now().minusHours(25)).build();
        when(requests.findMatching(anyString(),anyString())).thenReturn(List.of(previous));
        assertEquals(CompanyRegistrationStatus.PENDING,service.create(input()).registration().status());
        assertEquals(CompanyRegistrationStatus.REJECTED,previous.getStatus());
    }
    @Test void missingOrUnknownStatusTokenRevealsNothing() {
        assertThrows(ResourceNotFoundException.class,()->service.status(null));
        assertThrows(ResourceNotFoundException.class,()->service.status("123"));
        assertThrows(ResourceNotFoundException.class,()->service.status("A".repeat(43)));
    }
    @Test void rejectionRequiresReasonAndDoesNotCreateAccount() {
        var review=CompanyRegistrationReviewRequest.builder().adminComment(" ").build();
        assertThrows(BadRequestException.class,()->service.reject(1L,review));
        var entity=CompanyRegistrationRequest.builder().id(1L).status(CompanyRegistrationStatus.PENDING).build();
        when(requests.findForReview(1L)).thenReturn(Optional.of(entity));
        review.setAdminComment("Please explain the company activity");
        service.reject(1L,review);
        assertEquals(CompanyRegistrationStatus.REJECTED,entity.getStatus());
        assertNotNull(entity.getReviewedAt()); verify(users,never()).save(any());
    }
    @Test void approvalCreatesCompanyOnceAndUsesOriginalPasswordHash() {
        var entity=CompanyRegistrationRequest.builder().id(1L).status(CompanyRegistrationStatus.PENDING).representativeEmail("user@example.test")
                .contactEmail("contact@example.test").passwordHash("original-hash").build();
        when(requests.findForReview(1L)).thenReturn(Optional.of(entity));
        when(users.save(any())).thenAnswer(i->i.getArgument(0));
        when(companies.save(any())).thenAnswer(i->i.getArgument(0));
        var review=CompanyRegistrationReviewRequest.builder().adminComment("Approved").build();
        service.approve(1L,review);
        var captor=org.mockito.ArgumentCaptor.forClass(User.class);verify(users).save(captor.capture());
        assertEquals(Role.COMPANY,captor.getValue().getRole());assertEquals("original-hash",captor.getValue().getPassword());
        assertThrows(BadRequestException.class,()->service.approve(1L,review));
        verify(companies,times(1)).save(any());
    }
}
