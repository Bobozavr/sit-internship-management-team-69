package bg.tuvarna.sit.project.ps.internshipmanagement.repository;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.CompanyRegistrationRequest;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.CompanyRegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRegistrationRequestRepository extends JpaRepository<CompanyRegistrationRequest, Long> {

    List<CompanyRegistrationRequest> findByStatus(CompanyRegistrationStatus status);

    Optional<CompanyRegistrationRequest> findByContactEmail(String contactEmail);

    Optional<CompanyRegistrationRequest> findByRepresentativeEmail(String representativeEmail);

    boolean existsByContactEmail(String contactEmail);

    boolean existsByRepresentativeEmail(String representativeEmail);
}