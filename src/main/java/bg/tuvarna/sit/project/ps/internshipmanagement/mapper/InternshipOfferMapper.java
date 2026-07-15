package bg.tuvarna.sit.project.ps.internshipmanagement.mapper;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.InternshipOfferDto;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.InternshipOffer;
import org.springframework.stereotype.Component;

@Component
public class InternshipOfferMapper {        //only entity ->toDto bcs we need to know which company created offer

    public InternshipOfferDto toDto(InternshipOffer offer) {
        Long companyId = null;
        String companyName = null;      //if company no exist for some reason

        if (offer.getCompany() != null) {
            companyId = offer.getCompany().getId();
            companyName = offer.getCompany().getName();     //if offer exist in company take id and name and put toDto
        }

        return InternshipOfferDto.builder()
                .id(offer.getId())
                .title(offer.getTitle())
                .description(offer.getDescription())
                .requiredSkills(offer.getRequiredSkills())
                .location(offer.getLocation())
                .type(offer.getType())
                .deadline(offer.getDeadline())
                .status(offer.getStatus())
                .companyId(companyId)
                .companyName(companyName)
                .build();
    }
}