package bg.tuvarna.sit.project.ps.internshipmanagement.dto.companyregistration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRegistrationCreateRequest {         //form for Company

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description cannot be longer than 2000 characters")
    private String description;

    private String website;

    @NotBlank(message = "Contact email is required")
    @Email(message = "Contact email must be valid")
    private String contactEmail;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Representative first name is required")
    private String representativeFirstName;

    @NotBlank(message = "Representative last name is required")
    private String representativeLastName;

    @NotBlank(message = "Representative email is required")
    @Email(message = "Representative email must be valid")
    private String representativeEmail;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;            //but in entity we have PasswordHash bcs we wiil save hidden
}