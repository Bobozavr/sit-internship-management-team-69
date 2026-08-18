package bg.tuvarna.sit.project.ps.internshipmanagement.mapper;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.CompanyDto;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public CompanyDto toDto(Company company) {
        Long userId = null;
        String representativeName = null;
        String representativeEmail = null;

        if (company.getUser() != null) {            //form user entity
            userId = company.getUser().getId();
            representativeName = company.getUser().getFirstName()
                    + " "
                    + company.getUser().getLastName();
            representativeEmail = company.getUser().getEmail();
        }

        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .website(company.getWebsite())
                .contactEmail(company.getContactEmail())
                .city(company.getCity())
                .userId(userId)
                .representativeName(representativeName)
                .representativeEmail(representativeEmail)
                .build();
    }
}