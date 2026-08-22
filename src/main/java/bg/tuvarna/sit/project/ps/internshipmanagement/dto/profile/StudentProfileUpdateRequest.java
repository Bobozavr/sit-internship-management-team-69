package bg.tuvarna.sit.project.ps.internshipmanagement.dto.profile;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentProfileUpdateRequest {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank private String facultyNumber;
    @NotBlank private String specialty;
    @NotNull @Min(1) private Integer course;
    @NotBlank private String skills;
}
