package bg.tuvarna.sit.project.ps.internshipmanagement.dto;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.OfferStatus;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.WorkType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class InternshipOfferDto {
    private Long id;
    private String title;
    private String description;
    private String requiredSkills;
    private String location;
    private WorkType type;
    private LocalDate deadline;
    private OfferStatus status;
    private String companyName; // Удобнее передать имя, чем весь объект Company
}