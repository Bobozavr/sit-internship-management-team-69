package bg.tuvarna.sit.project.ps.internshipmanagement.dto.offer;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.WorkType;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InternshipOfferRequest {
    @NotBlank private String title;
    @NotBlank @Size(max=3000) private String description;
    @NotBlank @Size(max=1000) private String requiredSkills;
    @NotBlank private String location;
    @NotNull private WorkType type;
    @NotNull @FutureOrPresent private LocalDate deadline;
}
