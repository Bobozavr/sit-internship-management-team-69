package bg.tuvarna.sit.project.ps.internshipmanagement.controller;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.ApplicationDto;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.CompanyDto;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.InternshipOfferDto;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.StudentProfileDto;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.UserDto;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.admin.SystemStatsDto;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.admin.UserEnabledRequest;
import bg.tuvarna.sit.project.ps.internshipmanagement.service.AdminService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService s;

    public AdminController(AdminService s) {
        this.s = s;
    }

    @GetMapping("/users")
    public List<UserDto> users() {
        return s.users();
    }

    @PatchMapping("/users/{id}/enabled")
    public UserDto setEnabled(
            @PathVariable Long id,
            @Valid @RequestBody UserEnabledRequest r) {

        return s.setEnabled(id, r.getEnabled());
    }

    @GetMapping("/companies")
    public List<CompanyDto> companies() {
        return s.companies();
    }

    @GetMapping("/students")
    public List<StudentProfileDto> students() {
        return s.students();
    }

    @GetMapping("/offers")
    public List<InternshipOfferDto> offers() {
        return s.offers();
    }

    @GetMapping("/applications")
    public List<ApplicationDto> applications() {
        return s.applications();
    }

    @GetMapping("/stats")
    public SystemStatsDto stats() {
        return s.stats();
    }
}