package bg.tuvarna.sit.project.ps.internshipmanagement.dto.companyregistration;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRegistrationReviewRequest {     //admin dto approve/reject

    @Size(max = 2000, message = "Admin comment cannot be longer than 2000 characters")
    private String adminComment;
}