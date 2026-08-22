package bg.tuvarna.sit.project.ps.internshipmanagement.controller;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.companyregistration.CompanyRegistrationResponse;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.companyregistration.CompanyRegistrationReviewRequest;
import bg.tuvarna.sit.project.ps.internshipmanagement.service.CompanyRegistrationService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/company-registration-requests")
public class AdminCompanyRegistrationController {

    private final CompanyRegistrationService s;

    public AdminCompanyRegistrationController(
            CompanyRegistrationService s) {
        this.s = s;
    }

    @GetMapping
    public List<CompanyRegistrationResponse> all() {
        return s.all();
    }

    @GetMapping("/pending")
    public List<CompanyRegistrationResponse> pending() {
        return s.pending();
    }

    @GetMapping("/{id}")
    public CompanyRegistrationResponse get(
            @PathVariable Long id) {
        return s.get(id);
    }

    @PatchMapping("/{id}/approve")
    public CompanyRegistrationResponse approve(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRegistrationReviewRequest r) {

        return s.approve(id, r);
    }

    @PatchMapping("/{id}/reject")
    public CompanyRegistrationResponse reject(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRegistrationReviewRequest r) {

        return s.reject(id, r);
    }
}