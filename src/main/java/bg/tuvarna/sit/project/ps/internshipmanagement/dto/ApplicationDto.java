package bg.tuvarna.sit.project.ps.internshipmanagement.dto;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDto {
    private Long id;

    private LocalDateTime applicationDate;

    private String motivationLetter;

    private ApplicationStatus status;

    private String comment;

    private Long studentProfileId;

    private String studentName;

    private Long internshipOfferId;

    private String internshipTitle;

    private String companyName;
}