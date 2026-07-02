package bg.tuvarna.sit.project.ps.internshipmanagement.repository;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);  //is it unique?
}