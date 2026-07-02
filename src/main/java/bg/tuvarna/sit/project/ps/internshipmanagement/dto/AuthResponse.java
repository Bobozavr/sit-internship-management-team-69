package bg.tuvarna.sit.project.ps.internshipmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String email;
    private String role;
}
