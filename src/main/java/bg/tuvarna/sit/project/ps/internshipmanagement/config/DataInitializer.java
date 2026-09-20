package bg.tuvarna.sit.project.ps.internshipmanagement.config;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.User;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.AuthProvider;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.enums.Role;
import bg.tuvarna.sit.project.ps.internshipmanagement.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createAdmin(
            UserRepository users,
            PasswordEncoder encoder,
            @Value("${app.admin.email:admin@example.com}") String email,
            @Value("${app.admin.password:admin12345}") String password) {

        return args -> {
            if (!users.existsByEmail(email)) {
                users.save(
                        User.builder()
                                .firstName("System")
                                .lastName("Administrator")
                                .email(email)
                                .password(encoder.encode(password))
                                .role(Role.ADMIN)
                                .authProvider(AuthProvider.LOCAL)
                                .enabled(true)
                                .build()
                );
            }
        };
    }
}