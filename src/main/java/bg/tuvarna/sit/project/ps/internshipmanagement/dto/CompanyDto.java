package bg.tuvarna.sit.project.ps.internshipmanagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {
    private Long id;

    private String name;

    private String description;

    private String website;

    private String contactEmail;

    private String city;

    private Long userId;

    private String representativeName;

    private String representativeEmail;
}
