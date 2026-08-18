package bg.tuvarna.sit.project.ps.internshipmanagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileDto {

    private Long id;

    private String facultyNumber;

    private String specialty;

    private Integer course;

    private String skills;

    private Long userId;

    private String studentName;

    private String studentEmail;
}
