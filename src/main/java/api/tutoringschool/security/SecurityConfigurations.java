package api.tutoringschool.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    private static final String[] WHITELIST = {
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/users/profile-image").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/schools/profile-image").hasRole("TUTOR")
                        .requestMatchers(HttpMethod.POST, "/schools").hasRole("TUTOR")
                        .requestMatchers(HttpMethod.PUT, "/schools").hasRole("TUTOR")
                        .requestMatchers(HttpMethod.DELETE, "/schools").hasRole("TUTOR")
                        .requestMatchers(HttpMethod.POST, "/subjects").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/subjects").hasRole("TUTOR")
                        .requestMatchers(HttpMethod.DELETE, "/subjects").hasRole("TUTOR")
                        .requestMatchers(HttpMethod.POST, "/students").hasRole("GUARDIAN")
                        .requestMatchers(HttpMethod.PUT, "/students").hasRole("GUARDIAN")
                        .requestMatchers(HttpMethod.DELETE, "/students").hasRole("GUARDIAN")
                        .requestMatchers(HttpMethod.POST, "/announcements").hasRole("TUTOR")
                        .requestMatchers(HttpMethod.PUT, "/announcements").hasRole("TUTOR")
                        .requestMatchers(HttpMethod.DELETE, "/announcements").hasRole("TUTOR")
                        .requestMatchers(WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}