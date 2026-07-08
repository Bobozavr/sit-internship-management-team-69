package bg.tuvarna.sit.project.ps.internshipmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    //no need column we just saved fact he is in system
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)        //from where user came
    @Column(nullable = false)
    private AuthProvider authProvider;

    @Column(unique = true)      //id out of system
    private String externalId;

    @Column(nullable = false)       //for admin understanding
    private Boolean enabled;

    @PrePersist
    public void setDefaultValuesBeforeInsert() {
        this.createdAt = LocalDateTime.now();

        if (this.enabled == null) {
            this.enabled = true;
        }

        if (this.authProvider == null) {
            this.authProvider = AuthProvider.LOCAL;
        }
    }
}