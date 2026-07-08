package bg.tuvarna.sit.project.ps.internshipmanagement.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public CustomOAuth2SuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");

        if (email == null) {
            response.sendRedirect("/login?error=no_email");
            return;
        }

        if (email.endsWith("@tu-varna.bg")) {
            userService.processStudent(oauth2User);
            response.sendRedirect("/student/dashboard");
        } else if (isCompanyDomain(email)) {
            userService.processCompany(oauth2User);
            response.sendRedirect("/company/dashboard");
        } else {
            response.sendRedirect("/login?error=unauthorized");
        }
    }

    private boolean isCompanyDomain(String email) {
        return email.endsWith("@company.com");
    }
}