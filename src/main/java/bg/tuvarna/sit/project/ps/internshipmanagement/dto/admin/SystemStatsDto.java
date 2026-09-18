package bg.tuvarna.sit.project.ps.internshipmanagement.dto.admin;

import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SystemStatsDto {
    private long users;
    private long students;
    private long companies;
    private long pendingCompanyRequests;
    private long internshipOffers;
    private long activeOffers;
    private long applications;
}
