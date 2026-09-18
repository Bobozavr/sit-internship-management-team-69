package bg.tuvarna.sit.project.ps.internshipmanagement.controller;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.application.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    private final ApplicationService s;

    public ApplicationController(ApplicationService s) {
        this.s = s;
    }

    @PostMapping("/student/applications")
    public ResponseEntity<ApplicationDto> apply(
            @Valid @RequestBody ApplicationRequest r) {

        return ResponseEntity.status(HttpStatus.CREATED).body(s.apply(r));
    }

    @GetMapping("/student/applications")
    public List<ApplicationDto> student() {
        return s.studentApps();
    }

    @GetMapping("/student/applications/{id}")
    public ApplicationDto studentGet(@PathVariable Long id) {
        return s.studentGet(id);
    }

    @GetMapping("/company/applications")
    public List<ApplicationDto> company() {
        return s.companyApps();
    }

    @GetMapping("/company/offers/{offerId}/applications")
    public List<ApplicationDto> offer(@PathVariable Long offerId) {
        return s.offerApps(offerId);
    }

    @PatchMapping("/company/applications/{applicationId}/status")
    public ApplicationDto status(
            @PathVariable Long applicationId,
            @Valid @RequestBody ApplicationStatusRequest r) {

        return s.updateStatus(applicationId, r);
    }
}