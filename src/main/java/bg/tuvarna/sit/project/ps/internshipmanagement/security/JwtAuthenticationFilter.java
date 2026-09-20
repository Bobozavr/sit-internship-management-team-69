package bg.tuvarna.sit.project.ps.internshipmanagement.security;

import bg.tuvarna.sit.project.ps.internshipmanagement.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwt;
    private final UserRepository users;
    public JwtAuthenticationFilter(JwtService jwt, UserRepository users) {
        this.jwt = jwt; this.users = users;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwt.isValid(token)) {
                Long id = null;
                try { id = jwt.getUserId(token); } catch (IllegalArgumentException ignored) { }
                if (id != null) {
                    // Re-read account status and role on every request, including already issued tokens.
                    users.findById(id).filter(user -> Boolean.TRUE.equals(user.getEnabled()) && jwt.getVersion(token) == user.getTokenVersion()).ifPresent(user -> {
                        var authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null,
                                List.of(new SimpleGrantedAuthority(user.isPasswordChangeRequired() ? "PASSWORD_CHANGE_REQUIRED" : "ROLE_" + user.getRole().name())));
                        authentication.setDetails(user.getId());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    });
                }
            }
        }
        chain.doFilter(request, response);
    }
}
