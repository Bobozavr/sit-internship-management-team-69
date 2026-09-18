package bg.tuvarna.sit.project.ps.internshipmanagement.dto.auth;

import bg.tuvarna.sit.project.ps.internshipmanagement.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeResponse {

    private UserDto user;
}