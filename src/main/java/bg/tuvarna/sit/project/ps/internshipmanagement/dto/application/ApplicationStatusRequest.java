package bg.tuvarna.sit.project.ps.internshipmanagement.dto.application;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApplicationStatusRequest {
    @NotNull private ApplicationStatus status;
    @Size(max=2000) private String comment;
}
