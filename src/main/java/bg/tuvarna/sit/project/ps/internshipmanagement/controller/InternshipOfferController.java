package bg.tuvarna.sit.project.ps.internshipmanagement.controller;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.InternshipOfferDto;
import bg.tuvarna.sit.project.ps.internshipmanagement.repository.InternshipOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class InternshipOfferController {

    private final InternshipOfferRepository offerRepository;

    @GetMapping
    public List<InternshipOfferDto> getAllOffers() {
        return offerRepository.findAll().stream()
                .map(offer -> InternshipOfferDto.builder()
                        .id(offer.getId())
                        .title(offer.getTitle())
                        .description(offer.getDescription())
                        // Добавь здесь остальные поля из твоего InternshipOfferDto
                        .build())
                .collect(Collectors.toList());
    }
}