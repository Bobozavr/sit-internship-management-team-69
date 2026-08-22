package bg.tuvarna.sit.project.ps.internshipmanagement.controller;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.profile.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.service.*;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfileController {

    private final StudentProfileService students;
    private final CompanyService companies;

    public ProfileController(
            StudentProfileService s,
            CompanyService c) {

        students = s;
        companies = c;
    }

    @GetMapping("/student/profile")
    public StudentProfileDto student() {
        return students.get();
    }

    @PutMapping("/student/profile")
    public StudentProfileDto updateStudent(
            @Valid @RequestBody StudentProfileUpdateRequest r) {

        return students.update(r);
    }

    @GetMapping("/company/profile")
    public CompanyDto company() {
        return companies.get();
    }

    @PutMapping("/company/profile")
    public CompanyDto updateCompany(
            @Valid @RequestBody CompanyProfileUpdateRequest r) {

        return companies.update(r);
    }
}