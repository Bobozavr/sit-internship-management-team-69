package bg.tuvarna.sit.project.ps.internshipmanagement.repository;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.CompanyRegistrationRequest;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.CompanyRegistrationStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.*;
public interface CompanyRegistrationRequestRepository extends JpaRepository<CompanyRegistrationRequest, Long> {
    List<CompanyRegistrationRequest> findByStatus(CompanyRegistrationStatus status);
    Optional<CompanyRegistrationRequest> findByStatusTokenHash(String hash);
    @Query("select r from CompanyRegistrationRequest r where lower(r.representativeEmail) = :email or lower(r.contactEmail) = :contact")
    List<CompanyRegistrationRequest> findMatching(@Param("email") String email, @Param("contact") String contact);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from CompanyRegistrationRequest r where r.id = :id")
    Optional<CompanyRegistrationRequest> findForReview(@Param("id") Long id);
}
