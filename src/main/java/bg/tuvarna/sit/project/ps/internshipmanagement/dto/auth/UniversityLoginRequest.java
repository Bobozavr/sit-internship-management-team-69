package bg.tuvarna.sit.project.ps.internshipmanagement.dto.auth;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UniversityLoginRequest {
    @NotBlank private String facultyNumber;
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank @Email private String email;
    @NotBlank private String specialty;
    @NotNull @Min(1) private Integer course;
    @NotBlank private String skills;
}
