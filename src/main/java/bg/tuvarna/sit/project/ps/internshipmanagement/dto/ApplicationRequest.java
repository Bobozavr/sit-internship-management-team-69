package bg.tuvarna.sit.project.ps.internshipmanagement.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class ApplicationRequest {
    @NotBlank @Size(max=3000)
    private String motivationLetter;
    @NotNull
    private Long internshipOfferId;
}