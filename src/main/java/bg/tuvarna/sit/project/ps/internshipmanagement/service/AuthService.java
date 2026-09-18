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

    private AuthResponse token(User user) {
        return AuthResponse.builder().token(jwt.generateToken(user.getId(), user.getEmail(), user.getRole().name()))
                .email(user.getEmail()).role(user.getRole().name()).build();
    }
}
