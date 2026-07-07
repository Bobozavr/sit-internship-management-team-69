package bg.tuvarna.sit.project.ps.internshipmanagement.controller;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.ApplicationDto;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.ApplicationRequest;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.Application;
import bg.tuvarna.sit.project.ps.internshipmanagement.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationRepository applicationRepository;

    @GetMapping
    public List<ApplicationDto> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(app -> ApplicationDto.builder()
                        .id(app.getId())
                        .motivationLetter(app.getMotivationLetter())
                        .status(app.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping
    public ApplicationDto createApplication(@RequestBody ApplicationRequest request) {
        Application app = new Application();
        app.setMotivationLetter(request.getMotivationLetter());
        app.setApplicationDate(LocalDateTime.now());
        // Добавь привязку к офферу и студенту через repository.findById, если нужно

        Application savedApp = applicationRepository.save(app);

        return ApplicationDto.builder()
                .id(savedApp.getId())
                .motivationLetter(savedApp.getMotivationLetter())
                .build();
    }
}