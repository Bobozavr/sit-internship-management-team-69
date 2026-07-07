package bg.tuvarna.sit.project.ps.internshipmanagement.controller;

import bg.tuvarna.sit.project.ps.internshipmanagement.repository.InternshipOfferRepository;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.InternshipOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class InternshipOfferController {

    private final InternshipOfferRepository offerRepository;

    @GetMapping
    public List<InternshipOffer> getAllOffers() {
        return offerRepository.findAll();
    }
}