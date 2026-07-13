package bg.tuvarna.sit.project.ps.internshipmanagement.mapper;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.companyregistration.CompanyRegistrationCreateRequest;
import bg.tuvarna.sit.project.ps.internshipmanagement.dto.companyregistration.CompanyRegistrationResponse;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.CompanyRegistrationRequest;
import org.springframework.stereotype.Component;

@Component      //let use this class to other classes
public class CompanyRegistrationMapper {

    public CompanyRegistrationRequest toEntity(
            CompanyRegistrationCreateRequest request,
            String passwordHash
    ) {
        return CompanyRegistrationRequest.builder()
                .companyName(request.getCompanyName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .contactEmail(request.getContactEmail())
                .city(request.getCity())
                .representativeFirstName(request.getRepresentativeFirstName())
                .representativeLastName(request.getRepresentativeLastName())
                .representativeEmail(request.getRepresentativeEmail())
                .passwordHash(passwordHash)
                .build();
    }

    public CompanyRegistrationResponse toResponse(
            CompanyRegistrationRequest request,
            String message
    ) {
        Long createdCompanyId = null;

        if (request.getCreatedCompany() != null) {      //checking if company exist
            createdCompanyId = request.getCreatedCompany().getId();
        }

        return CompanyRegistrationResponse.builder()
                .id(request.getId())
                .companyName(request.getCompanyName())
                .contactEmail(request.getContactEmail())
                .representativeEmail(request.getRepresentativeEmail())
                .status(request.getStatus())
                .adminComment(request.getAdminComment())
                .requestedAt(request.getRequestedAt())
                .reviewedAt(request.getReviewedAt())
                .createdCompanyId(createdCompanyId)
                .message(message)
                .build();
    }
}