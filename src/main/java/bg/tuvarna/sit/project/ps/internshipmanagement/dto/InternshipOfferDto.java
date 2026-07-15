package bg.tuvarna.sit.project.ps.internshipmanagement.dto;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.OfferStatus;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.WorkType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    private Long companyId;

    private String companyName; // Удобнее передать имя, чем весь объект Company
}