package bg.tuvarna.sit.project.ps.internshipmanagement.controller;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.companyregistration.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.service.CompanyRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/company-registration-requests")
public class CompanyRegistrationController {
    private final CompanyRegistrationService service;
    public CompanyRegistrationController(CompanyRegistrationService service) { this.service=service; }
    @PostMapping
    public ResponseEntity<RegistrationReceipt> create(@Valid @RequestBody CompanyRegistrationCreateRequest request) {
        return ResponseEntity.status(201).cacheControl(CacheControl.noStore()).body(service.create(request));
    }
    @GetMapping("/status")
    public ResponseEntity<RegistrationStatusResponse> status(@RequestHeader(value="X-Registration-Token",required=false) String token) {
        return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(service.status(token));
    }
}
