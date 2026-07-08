package bg.tuvarna.sit.project.ps.internshipmanagement.security;

import bg.tuvarna.sit.project.ps.internshipmanagement.repository.UserRepository;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.User; // Убедитесь в верном импорте вашей сущности
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.Role; // Убедитесь в верном импорте Role
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Service
public class UserService {
    private final UserRepository userRepository;

    // Явный конструктор (Spring сам внедрит UserRepository)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void processStudent(OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");
        userRepository.findByEmail(email).ifPresentOrElse(
                user -> {},
                () -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setRole(Role.STUDENT);
                    userRepository.save(newUser);
                }
        );
    }

    public void processCompany(OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");
        userRepository.findByEmail(email).ifPresentOrElse(
                user -> {},
                () -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setRole(Role.COMPANY);
                    userRepository.save(newUser);
                }
        );
    }
}