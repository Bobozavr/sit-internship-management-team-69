package bg.tuvarna.sit.project.ps.internshipmanagement.dto;

import lombok.Data;

@Data
public class CompanyReviewRequest {
    private Long requestId;
    private boolean approved;
    private String adminComment;
}