package com.westminster.doctor_service.config;

import com.westminster.doctor_service.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ✅ use lambda
                .authorizeHttpRequests(auth -> auth
                        // Swagger endpoints
                        .requestMatchers("/api/v1/doctors/all","/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**","/doctor/create").permitAll()

                        // Doctor APIs
                        .requestMatchers("/api/v1/doctors/**").hasAnyRole("ADMIN", "DOCTOR")  // list/view
                        //.requestMatchers("/api/v1/doctors/**").hasRole("ADMIN")            // create/delete/update

                        // Everything else
                        .anyRequest().authenticated()
                )
                // ✅ Add your JWT filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
