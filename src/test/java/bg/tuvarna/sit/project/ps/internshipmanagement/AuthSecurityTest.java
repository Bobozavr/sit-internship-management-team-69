package bg.tuvarna.sit.project.ps.internshipmanagement;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.auth.UniversityLoginRequest;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.User;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.Role;
import bg.tuvarna.sit.project.ps.internshipmanagement.exception.BadRequestException;
import bg.tuvarna.sit.project.ps.internshipmanagement.mapper.UserMapper;
import bg.tuvarna.sit.project.ps.internshipmanagement.repository.UserRepository;
import bg.tuvarna.sit.project.ps.internshipmanagement.security.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.service.AuthService;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthSecurityTest {
    UserRepository users = mock(UserRepository.class);
    PasswordEncoder encoder = mock(PasswordEncoder.class);
    JwtService jwt = new JwtService("test-only-secret-with-more-than-32-characters", 60000);
    AuthService auth = new AuthService(users, encoder, jwt, new UserMapper());
    User student = User.builder().id(42L).email("student@example.test").role(Role.STUDENT).enabled(true).password("hash").build();

    @AfterEach void clearContext() { SecurityContextHolder.clearContext(); }

    @Test void universityLoginRequiresExistingAccount() {
        when(users.findByEmail(student.getEmail())).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> auth.universityLogin(new UniversityLoginRequest(student.getEmail(), "password")));
        verify(users, never()).save(any());
    }
    @Test void universityLoginRequiresPasswordAndDoesNotReenableAccount() {
        when(users.findByEmail(student.getEmail())).thenReturn(Optional.of(student));
        assertThrows(BadRequestException.class, () -> auth.universityLogin(new UniversityLoginRequest(student.getEmail(), "wrong")));
        student.setEnabled(false);
        when(encoder.matches("correct", "hash")).thenReturn(true);
        assertThrows(BadRequestException.class, () -> auth.universityLogin(new UniversityLoginRequest(student.getEmail(), "correct")));
        assertFalse(student.getEnabled());
        verify(users, never()).save(any());
    }
    @Test void validStudentCanSignInWithoutChangingProfile() {
        when(users.findByEmail(student.getEmail())).thenReturn(Optional.of(student));
        when(encoder.matches("correct", "hash")).thenReturn(true);
        var response = auth.universityLogin(new UniversityLoginRequest(student.getEmail(), "correct"));
        assertEquals("STUDENT", response.getRole());
        assertTrue(jwt.isValid(response.getToken()));
        verify(users, never()).save(any());
    }
    @Test void issuedTokenStopsWorkingWhenAccountIsBlocked() throws Exception {
        when(users.findById(42L)).thenReturn(Optional.of(student));
        String token = jwt.generateToken(42L, student.getEmail(), "ADMIN");
        var filter = new JwtAuthenticationFilter(jwt, users);
        var request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);
        filter.doFilter(request, new MockHttpServletResponse(), new MockFilterChain());
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals("ROLE_STUDENT", authentication.getAuthorities().iterator().next().getAuthority());
        SecurityContextHolder.clearContext();
        student.setEnabled(false);
        filter.doFilter(request, new MockHttpServletResponse(), new MockFilterChain());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test void temporaryPasswordTokenHasNoStudentPrivileges() throws Exception {
        student.setPasswordChangeRequired(true);
        when(users.findById(42L)).thenReturn(Optional.of(student));
        var request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwt.generateToken(42L, student.getEmail(), "STUDENT", 0));
        new JwtAuthenticationFilter(jwt, users).doFilter(request, new MockHttpServletResponse(), new MockFilterChain());
        assertEquals("PASSWORD_CHANGE_REQUIRED", SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority());
    }
    @Test void passwordChangeInvalidatesOldTokenAndEnablesAccount() throws Exception {
        student.setPasswordChangeRequired(true);
        when(users.findById(42L)).thenReturn(Optional.of(student));
        when(encoder.matches("temporary", "hash")).thenReturn(true);
        when(encoder.encode("new-password-123")).thenReturn("new-hash");
        String oldToken=jwt.generateToken(42L, student.getEmail(), "STUDENT", 0);
        var response=auth.changePassword(42L, new bg.tuvarna.sit.project.ps.internshipmanagement.dto.auth.ChangePasswordRequest("temporary", "new-password-123"));
        assertFalse(student.isPasswordChangeRequired());
        assertEquals("new-hash", student.getPassword());
        assertEquals(1, jwt.getVersion(response.getToken()));
        var request=new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + oldToken);
        new JwtAuthenticationFilter(jwt, users).doFilter(request, new MockHttpServletResponse(), new MockFilterChain());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
    @Test void wrongCurrentPasswordCannotChangeCredentials() {
        when(users.findById(42L)).thenReturn(Optional.of(student));
        assertThrows(BadRequestException.class, () -> auth.changePassword(42L,
            new bg.tuvarna.sit.project.ps.internshipmanagement.dto.auth.ChangePasswordRequest("wrong", "new-password-123")));
        assertEquals("hash", student.getPassword());
        assertEquals(0, student.getTokenVersion());
        verify(users, never()).save(any());
    }
    @Test void studentCourseIsLimitedToFour() {
        try (var factory = jakarta.validation.Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            assertTrue(validator.validate(new bg.tuvarna.sit.project.ps.internshipmanagement.dto.admin.CreateStudentRequest("Test", "Student", "12345", "Software", 4)).isEmpty());
            assertFalse(validator.validate(new bg.tuvarna.sit.project.ps.internshipmanagement.dto.admin.CreateStudentRequest("Test", "Student", "12345", "Software", 5)).isEmpty());
        }
    }
}
