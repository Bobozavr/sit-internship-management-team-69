package bg.tuvarna.sit.project.ps.internshipmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "applications",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_student_offer_application",
                        columnNames = {"student_profile_id", "internship_offer_id"}
                )
        }
)           //to not let students submit 2 times
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime applicationDate;

    @Column(nullable = false, length = 3000)
    private String motivationLetter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(length = 2000)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_profile_id", nullable = false)
    private StudentProfile studentProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internship_offer_id", nullable = false)
    private InternshipOffer internshipOffer;

    @PrePersist
    public void setApplicationDateBeforeInsert() {
        this.applicationDate = LocalDateTime.now();

        if (this.status == null) {
            this.status = ApplicationStatus.PENDING;
        }
    }
}