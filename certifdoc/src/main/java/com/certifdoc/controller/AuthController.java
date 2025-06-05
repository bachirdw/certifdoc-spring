package com.certifdoc.controller;

import com.certifdoc.entity.AuthRequest;
import com.certifdoc.entity.AuthResponse;
import com.certifdoc.entity.RoleEntity;
import com.certifdoc.entity.UserEntity;
import com.certifdoc.repository.UserRepository;
import com.certifdoc.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200") 
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        // Authentifie l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()
            )
        );
    
        // Utilise Optional avec orElseThrow
        UserEntity user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    
        // Génère le token JWT
        String token = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
    
        // Retourne les infos nécessaires
        return ResponseEntity.ok(new AuthResponse(
            token,
            user.getEmail(),
            user.getRole().name()
        ));
    }
}    