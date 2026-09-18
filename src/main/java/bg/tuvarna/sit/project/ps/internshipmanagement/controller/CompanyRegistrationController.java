package bg.tuvarna.sit.project.ps.internshipmanagement.controller;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.companyregistration.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.service.CompanyRegistrationService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company-registration-requests")
public class CompanyRegistrationController {

    private final CompanyRegistrationService s;

    public CompanyRegistrationController(CompanyRegistrationService s) {
        this.s = s;
    }

    @PostMapping
    public org.springframework.http.ResponseEntity<CompanyRegistrationResponse> create(
            @Valid @RequestBody CompanyRegistrationCreateRequest r) {

        return org.springframework.http.ResponseEntity
                .status(HttpStatus.CREATED)
                .body(s.create(r));
    }

    @GetMapping("/{id}/status")
    public CompanyRegistrationResponse status(@PathVariable Long id) {
        return s.get(id);
    }
}