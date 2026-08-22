package bg.tuvarna.sit.project.ps.internshipmanagement.controller;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.auth.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService s) {
        service = s;
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody AuthRequest r) {

        return service.login(r);
    }

    @PostMapping("/university-login")
    public AuthResponse university(
            @Valid @RequestBody UniversityLoginRequest r) {

        return service.universityLogin(r);
    }

    @GetMapping("/me")
    public UserDto me() {

        Object d = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getDetails();

        return service.me((Long) d);
    }
}