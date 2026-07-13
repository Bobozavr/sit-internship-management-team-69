package bg.tuvarna.sit.project.ps.internshipmanagement.dto.companyregistration;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.CompanyRegistrationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRegistrationResponse {      //backend which will return response

    private Long id;

    private String companyName;

    private String contactEmail;

    private String representativeEmail;

    private CompanyRegistrationStatus status;

    private String adminComment;

    private LocalDateTime requestedAt;

    private LocalDateTime reviewedAt;

    private Long createdCompanyId;

    private String message;
}