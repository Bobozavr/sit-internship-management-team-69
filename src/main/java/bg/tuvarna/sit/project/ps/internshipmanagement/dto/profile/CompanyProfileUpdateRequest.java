package bg.tuvarna.sit.project.ps.internshipmanagement.dto.profile;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyProfileUpdateRequest {
    @NotBlank private String name;
    @NotBlank @Size(max=2000) private String description;
    private String website;
    @NotBlank @Email private String contactEmail;
    @NotBlank private String city;
    @NotBlank private String representativeFirstName;
    @NotBlank private String representativeLastName;
}
