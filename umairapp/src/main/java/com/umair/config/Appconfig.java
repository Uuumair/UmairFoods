package com.umair.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class Appconfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management -> 
                management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless for JWT
            .authorizeHttpRequests(auth -> 
                auth.requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER", "ADMIN")
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()) // Permit all other requests
            .addFilterBefore(new JwtTokenValidation(), BasicAuthenticationFilter.class) // JWT Token validation filter
            .csrf(csrf -> csrf.disable()) // Disable CSRF (since we are using JWT)
            .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Enable CORS
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // React app URL
                cfg.setAllowedMethods(Collections.singletonList("*")); // Allow all methods
                cfg.setAllowCredentials(true); // Allow credentials
                cfg.setAllowedHeaders(Collections.singletonList("*")); // Allow all headers
                cfg.setExposedHeaders(Arrays.asList("Authorization")); // Expose Authorization header
                cfg.setMaxAge(3600L); // Cache CORS preflight response for 1 hour
                return cfg;
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // For password encryption
    }
}
