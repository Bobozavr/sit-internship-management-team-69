package bg.tuvarna.sit.project.ps.internshipmanagement.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}