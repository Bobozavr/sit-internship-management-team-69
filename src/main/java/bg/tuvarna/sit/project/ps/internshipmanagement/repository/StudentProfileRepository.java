package bg.tuvarna.sit.project.ps.internshipmanagement.repository;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {

    Optional<StudentProfile> findByUserId(Long userId); //profile search

    boolean existsByFacultyNumber(String facultyNumber); //is it unique?
}