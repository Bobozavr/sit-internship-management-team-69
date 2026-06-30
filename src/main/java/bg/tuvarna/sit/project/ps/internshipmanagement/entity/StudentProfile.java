package bg.tuvarna.sit.project.ps.internshipmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String facultyNumber;

    @Column(nullable = false)
    private String specialty;

    @Column(nullable = false)
    private Integer course;

    @Column(nullable = false, length = 1000)
    private String skills;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}