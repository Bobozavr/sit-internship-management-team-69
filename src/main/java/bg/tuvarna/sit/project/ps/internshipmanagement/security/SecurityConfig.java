package bg.tuvarna.sit.project.ps.internshipmanagement.security;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;
import java.util.List;

@Configuration @EnableMethodSecurity
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    @Bean SecurityFilterChain filterChain(HttpSecurity http,JwtAuthenticationFilter filter)throws Exception{
        http.exceptionHandling(e -> e.authenticationEntryPoint((request, response, exception) -> response.setStatus(401)).accessDeniedHandler((request, response, exception) -> response.setStatus(403))).csrf(c->c.disable()).cors(c->c.configurationSource(corsConfigurationSource())).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(a->a
          .requestMatchers("/api/auth/login","/api/auth/university-login","/api/company-registration-requests/**","/swagger-ui/**","/v3/api-docs/**").permitAll()
          .requestMatchers(HttpMethod.GET,"/api/offers/**").permitAll()
          .requestMatchers("/api/auth/me", "/api/auth/password").authenticated()
          .requestMatchers("/api/admin/**").hasRole("ADMIN")
          .requestMatchers("/api/company/**").hasRole("COMPANY")
          .requestMatchers("/api/student/**").hasRole("STUDENT")
          .anyRequest().hasAnyRole("ADMIN", "COMPANY", "STUDENT")).addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); return http.build();
    }
    @Bean CorsConfigurationSource corsConfigurationSource(){CorsConfiguration c=new CorsConfiguration();c.setAllowedOrigins(List.of("http://localhost:3000","http://localhost:5173","http://127.0.0.1:5173"));c.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));c.setAllowedHeaders(List.of("*"));c.setAllowCredentials(true);UrlBasedCorsConfigurationSource s=new UrlBasedCorsConfigurationSource();s.registerCorsConfiguration("/**",c);return s;}
}
