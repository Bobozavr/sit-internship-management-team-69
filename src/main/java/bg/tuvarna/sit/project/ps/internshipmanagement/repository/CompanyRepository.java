package bg.tuvarna.sit.project.ps.internshipmanagement.repository;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByUserId(Long userId);    //search in DB email which was for login

    boolean existsByContactEmail(String contactEmail);      //for registr is it already exist?
}