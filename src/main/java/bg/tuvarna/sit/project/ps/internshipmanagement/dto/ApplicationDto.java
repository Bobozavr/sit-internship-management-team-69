package bg.tuvarna.sit.project.ps.internshipmanagement.dto;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationDto {
    private Long id;
    private LocalDateTime applicationDate;
    private String motivationLetter;
    private ApplicationStatus status;
    private String comment;
    private Long studentProfileId;
    private Long internshipOfferId;
}