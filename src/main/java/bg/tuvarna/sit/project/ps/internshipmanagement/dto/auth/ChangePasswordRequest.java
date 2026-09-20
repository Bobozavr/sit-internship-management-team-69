package bg.tuvarna.sit.project.ps.internshipmanagement.dto.auth;
import jakarta.validation.constraints.*;
public record ChangePasswordRequest(@NotBlank String currentPassword,
        @NotBlank @Size(min=10,max=64) String newPassword) { }
