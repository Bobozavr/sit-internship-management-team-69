package bg.tuvarna.sit.project.ps.internshipmanagement.dto.auth;
import jakarta.validation.constraints.*;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UniversityLoginRequest {
    @NotBlank @Email private String email;
    @NotBlank private String password;
}
