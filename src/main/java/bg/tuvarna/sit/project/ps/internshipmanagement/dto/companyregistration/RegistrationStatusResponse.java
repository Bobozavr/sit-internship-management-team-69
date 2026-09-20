package bg.tuvarna.sit.project.ps.internshipmanagement.dto.companyregistration;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.CompanyRegistrationStatus;
import java.time.LocalDateTime;
public record RegistrationStatusResponse(String companyName, CompanyRegistrationStatus status,
        String adminComment, LocalDateTime requestedAt, LocalDateTime reviewedAt, LocalDateTime resubmitAt) { }
