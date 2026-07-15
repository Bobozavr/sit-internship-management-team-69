package bg.tuvarna.sit.project.ps.internshipmanagement.dto;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
