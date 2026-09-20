package bg.tuvarna.sit.project.ps.internshipmanagement.controller;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.admin.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.service.StudentAccountService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/admin/student-accounts")
public class StudentAccountController {
    private final StudentAccountService service;
    public StudentAccountController(StudentAccountService service) { this.service=service; }
    @PostMapping
    public ResponseEntity<StudentCredentials> create(@Valid @RequestBody CreateStudentRequest request) {
        return ResponseEntity.status(201).cacheControl(CacheControl.noStore()).body(service.create(request));
    }
    @PostMapping("/{id}/reset-password")
    public ResponseEntity<StudentCredentials> reset(@PathVariable Long id) {
        return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(service.reset(id));
    }
}
