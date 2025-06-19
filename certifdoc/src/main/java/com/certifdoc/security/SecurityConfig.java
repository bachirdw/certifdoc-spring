package com.certifdoc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .cors(withDefaults()) // Activation du CORS
            .csrf(csrf -> csrf.disable()) // Désactivation CSRF
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
             // Autorise les requêtes selon les rôles
            .requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole("FORMATEUR")
            .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("FORMATEUR")
            .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("FORMATEUR")

            .requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole("ADMIN")

            .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("AUDITEUR")
            // toutes les autres requêtes nécessitent une authentification
            .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Ajout du filtre JWT avant le filtre UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
           //construction de l'objet SecurityFilterChain
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}