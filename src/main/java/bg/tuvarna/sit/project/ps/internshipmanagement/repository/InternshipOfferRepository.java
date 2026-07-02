package bg.tuvarna.sit.project.ps.internshipmanagement.repository;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.InternshipOffer;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.OfferStatus;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternshipOfferRepository extends JpaRepository<InternshipOffer, Long> {

    List<InternshipOffer> findByStatus(OfferStatus status);  //User can see only for active offers

    List<InternshipOffer> findByCompanyId(Long companyId);  //only yourself company offers

    List<InternshipOffer> findByLocationContainingIgnoreCase(String location);  //search by location

    List<InternshipOffer> findByType(WorkType type); //search by type job

    List<InternshipOffer> findByRequiredSkillsContainingIgnoreCase(String skill);  //search by skills
}