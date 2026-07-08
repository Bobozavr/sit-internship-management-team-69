package bg.tuvarna.sit.project.ps.internshipmanagement.security;

import bg.tuvarna.sit.project.ps.internshipmanagement.entity.AuthProvider;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.Role;
import bg.tuvarna.sit.project.ps.internshipmanagement.entity.User;
import bg.tuvarna.sit.project.ps.internshipmanagement.repository.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final String UNKNOWN_NAME_PLACEHOLDER = "Unknown";

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void processStudent(OAuth2User oauth2User) {
        processUser(oauth2User, Role.STUDENT, AuthProvider.UNIVERSITY);
    }

    public void processCompany(OAuth2User oauth2User) {
        processUser(oauth2User, Role.COMPANY, AuthProvider.COMPANY_PROVIDER);
    }

    private void processUser(OAuth2User oauth2User, Role role, AuthProvider authProvider) {
        String email = oauth2User.getAttribute("email");

        userRepository.findByEmail(email).ifPresentOrElse(
                user -> {},
                () -> {
                    User newUser = User.builder()
                            .email(email)
                            .firstName(extractFirstName(oauth2User))
                            .lastName(extractLastName(oauth2User))
                            .role(role)
                            .authProvider(authProvider)
                            .externalId(oauth2User.getName())
                            .build();
                    userRepository.save(newUser);
                }
        );
    }

    private String extractFirstName(OAuth2User oauth2User) {
        String givenName = oauth2User.getAttribute("given_name");
        if (givenName != null && !givenName.isBlank()) {
            return givenName;
        }

        String[] nameParts = splitFullName(oauth2User);
        return nameParts.length > 0 ? nameParts[0] : UNKNOWN_NAME_PLACEHOLDER;
    }

    private String extractLastName(OAuth2User oauth2User) {
        String familyName = oauth2User.getAttribute("family_name");
        if (familyName != null && !familyName.isBlank()) {
            return familyName;
        }

        String[] nameParts = splitFullName(oauth2User);
        return nameParts.length > 1 ? nameParts[1] : UNKNOWN_NAME_PLACEHOLDER;
    }

    // "name" comes back as a single string (e.g. "Ivan Ivanov") when Azure AD
    // doesn't expose given_name/family_name separately, so we split it as a fallback.
    private String[] splitFullName(OAuth2User oauth2User) {
        String fullName = oauth2User.getAttribute("name");
        if (fullName == null || fullName.isBlank()) {
            return new String[0];
        }
        return fullName.trim().split("\\s+", 2);
    }
}