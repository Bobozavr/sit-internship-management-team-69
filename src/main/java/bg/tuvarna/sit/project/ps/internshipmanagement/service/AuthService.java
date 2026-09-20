package bg.tuvarna.sit.project.ps.internshipmanagement.service;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.auth.UniversityLoginRequest;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.User;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.Role;
import bg.tuvarna.sit.project.ps.internshipmanagement.exception.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.mapper.UserMapper;
import bg.tuvarna.sit.project.ps.internshipmanagement.repository.UserRepository;
import bg.tuvarna.sit.project.ps.internshipmanagement.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    private final UserMapper mapper;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwt, UserMapper mapper) {
        this.users = users; this.encoder = encoder; this.jwt = jwt; this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest request) {
        User user = authenticate(request.getEmail(), request.getPassword());
        if (user.getRole() == Role.STUDENT) throw invalidCredentials();
        return token(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse universityLogin(UniversityLoginRequest request) {
        User user = authenticate(request.getEmail(), request.getPassword());
        if (user.getRole() != Role.STUDENT) throw invalidCredentials();
        return token(user);
    }

    private User authenticate(String email, String password) {
        User user = users.findByEmail(email.trim()).orElseThrow(this::invalidCredentials);
        if (!Boolean.TRUE.equals(user.getEnabled()) || user.getPassword() == null
                || !encoder.matches(password, user.getPassword())) throw invalidCredentials();
        return user;
    }

    private BadRequestException invalidCredentials() {
        return new BadRequestException("Invalid email or password, or account disabled");
    }

    public UserDto me(Long id) {
        return mapper.toDto(users.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }

    @Transactional
    public AuthResponse changePassword(Long id, bg.tuvarna.sit.project.ps.internshipmanagement.dto.auth.ChangePasswordRequest request) {
        User user = users.findById(id).orElseThrow(this::invalidCredentials);
        if (!Boolean.TRUE.equals(user.getEnabled()) || user.getPassword() == null
                || !encoder.matches(request.currentPassword(), user.getPassword())) throw invalidCredentials();
        if (request.newPassword().getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 72)
            throw new BadRequestException("Password must fit within 72 UTF-8 bytes");
        if (request.newPassword().equals(request.currentPassword()))
            throw new BadRequestException("Choose a different password");
        user.setPassword(encoder.encode(request.newPassword()));
        user.setPasswordChangeRequired(false);
        user.setTokenVersion(user.getTokenVersion() + 1);
        users.save(user);
        return token(user);
    }

    private AuthResponse token(User user) {
        return AuthResponse.builder().token(jwt.generateToken(user.getId(), user.getEmail(), user.getRole().name(), user.getTokenVersion()))
                .email(user.getEmail()).role(user.getRole().name()).passwordChangeRequired(user.isPasswordChangeRequired()).build();
    }
}
