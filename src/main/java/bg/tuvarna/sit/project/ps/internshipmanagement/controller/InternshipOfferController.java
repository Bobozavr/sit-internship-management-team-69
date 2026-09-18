package bg.tuvarna.sit.project.ps.internshipmanagement.controller;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.offer.*;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.WorkType;
import bg.tuvarna.sit.project.ps.internshipmanagement.service.InternshipOfferService;

import jakarta.validation.Valid;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class InternshipOfferController {

    private final InternshipOfferService s;

    public InternshipOfferController(InternshipOfferService s) {
        this.s = s;
    }

    @GetMapping("/offers")
    public List<InternshipOfferDto> search(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) WorkType type,
            @RequestParam(required = false) String skill) {

        return s.search(location, type, skill);
    }

    @GetMapping("/offers/{id}")
    public InternshipOfferDto get(@PathVariable Long id) {
        return s.get(id);
    }

    @PostMapping("/company/offers")
    public ResponseEntity<InternshipOfferDto> create(
            @Valid @RequestBody InternshipOfferRequest r) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(s.create(r));
    }

    @GetMapping("/company/offers")
    public List<InternshipOfferDto> mine() {
        return s.mine();
    }

    @PutMapping("/company/offers/{id}")
    public InternshipOfferDto update(
            @PathVariable Long id,
            @Valid @RequestBody InternshipOfferRequest r) {

        return s.update(id, r);
    }

    @PatchMapping("/company/offers/{id}/close")
    public InternshipOfferDto close(@PathVariable Long id) {
        return s.close(id);
    }
}