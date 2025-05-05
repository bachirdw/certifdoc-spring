package com.certifdoc.controller;

import com.certifdoc.entity.User;
import com.certifdoc.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationLoginController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        // Vérification de l'existence de l'utilisateur par email et mot de passe
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null || !passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        return ResponseEntity.ok(existingUser);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Vérification de l'existence de l'utilisateur par email
        if (userRepository.findByEmail(user.getEmail())!= null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

User newUser = new User();
newUser.setFirstname(user.getFirstname());
newUser.setLastname(user.getLastname()); 
newUser.setEmail(user.getEmail());
newUser.setPassword(passwordEncoder.encode(user.getPassword())); // Mot de passe brut (non encodé ici, sera encodé plus tard)   

return ResponseEntity.ok(userRepository.save(newUser)); // Enregistrement de l'utilisateur dans la base de données

        }
         
}