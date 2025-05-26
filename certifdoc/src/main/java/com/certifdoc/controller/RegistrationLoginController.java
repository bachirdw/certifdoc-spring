package com.certifdoc.controller;

import com.certifdoc.entity.RoleEntity;
import com.certifdoc.entity.UserEntity;
import com.certifdoc.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

/**
 * Contrôleur REST pour la gestion de l'inscription et de la connexion des utilisateurs.
 * <p>
 * - Fournit les endpoints pour l'inscription (/register) et la connexion (/login) des utilisateurs.
 * - Utilise UserRepository pour accéder et sauvegarder les entités UserEntity en base de données.
 * - Utilise PasswordEncoder pour sécuriser le stockage et la vérification des mots de passe.
 * - Reçoit et retourne des objets UserEntity via des requêtes JSON.
 * - Lors de l'inscription, permet d'associer une formation à l'utilisateur (relation ManyToOne avec FormationEntity).
 * <p>
 * Interactions principales :
 * - UserEntity : entité représentant un utilisateur.
 * - UserRepository : accès aux opérations CRUD sur les utilisateurs.
 * - PasswordEncoder : encodage et vérification des mots de passe.
 * - FormationEntity : association possible lors de l'inscription.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
//authoriser requete de angular
@CrossOrigin("*")
public class RegistrationLoginController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserEntity user) {
        // Vérification de l'existence de l'utilisateur par email et mot de passe
        UserEntity existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null || !passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        return ResponseEntity.ok(existingUser);
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user) {
        UserEntity newUser = new UserEntity();
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        // Définir le rôle par défaut à FORMATEUR si non fourni
        newUser.setRole(RoleEntity.FORMATEUR);
        newUser.setActive(user.isActive());
        newUser.setNotLocked(user.isNotLocked());
        newUser.setFormation(user.getFormation());

        UserEntity savedUser = userRepository.save(newUser);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }
}