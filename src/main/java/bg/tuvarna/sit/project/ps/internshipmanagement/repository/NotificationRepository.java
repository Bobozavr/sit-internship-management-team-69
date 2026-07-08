package bg.tuvarna.sit.project.ps.internshipmanagement.repository;


import bg.tuvarna.sit.project.ps.internshipmanagement.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientIdOrderByCreatedAtDesc(Long userId);
}