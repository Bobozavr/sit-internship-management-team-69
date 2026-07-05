package bg.tuvarna.sit.project.ps.internshipmanagement.security;

import bg.tuvarna.sit.project.ps.internshipmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return new CustomUserDetails(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}