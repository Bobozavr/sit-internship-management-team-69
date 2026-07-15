package bg.tuvarna.sit.project.ps.internshipmanagement.entity;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.CompanyRegistrationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "company_registration_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRegistrationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Company information
    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false, length = 2000)
    private String description;

    private String website;

    @Column(nullable = false)
    private String contactEmail;

    @Column(nullable = false)
    private String city;

    // Representative information
    @Column(nullable = false)
    private String representativeFirstName;

    @Column(nullable = false)
    private String representativeLastName;

    @Column(nullable = false)
    private String representativeEmail;

    // Password that will be used only if admin approves the company
    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanyRegistrationStatus status;

    @Column(length = 2000)
    private String adminComment;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    private LocalDateTime reviewedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_company_id")
    private Company createdCompany;

    @PrePersist
    public void setDefaultValuesBeforeInsert() {
        this.requestedAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = CompanyRegistrationStatus.PENDING;
        }
    }
}