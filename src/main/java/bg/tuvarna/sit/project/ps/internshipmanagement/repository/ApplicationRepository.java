package bg.tuvarna.sit.project.ps.internshipmanagement.repository;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.Application;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByStudentProfileId(Long studentProfileId); //for student only to see his applications

    List<Application> findByInternshipOfferId(Long internshipOfferId);      //for company to see candidats

    List<Application> findByStatus(ApplicationStatus status);   //for filter by type of offer

    boolean existsByStudentProfileIdAndInternshipOfferId(Long studentProfileId, Long internshipOfferId); //is it unique?
}