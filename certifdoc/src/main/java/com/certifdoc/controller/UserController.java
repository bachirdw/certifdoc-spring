package com.certifdoc.controller;

import org.springframework.http.HttpHeaders; 
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.certifdoc.dto.UserDTO;
import com.certifdoc.entity.RoleEntity;
import com.certifdoc.entity.UserEntity;
import com.certifdoc.security.JwtUtil;
import com.certifdoc.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";

    @Autowired
    private UserService userService;

     // Enregistrer un nouvel utilisateur
     @Autowired
     private PasswordEncoder passwordEncoder;

     @Autowired
    private AuthenticationManager authenticationManager; 
   
    @Autowired
    private JwtUtil jwtUtil; 
   
    /**
     * Génère un en-tête HTTP avec un JWT pour un utilisateur donné.
     * Utilisé dans le login pour renvoyer le token dans la réponse.
     */
    private HttpHeaders getJwtHeader(UserEntity user) {
        HttpHeaders headers = new HttpHeaders();
    
        if (user == null || user.getEmail() == null) {
            throw new IllegalArgumentException("Utilisateur invalide ou email manquant pour la génération du JWT");
        }
    
        String token = jwtUtil.generateJwtToken(user);
        headers.set("Authorization", "Bearer " + token);
    
        return headers;
    }
    
    /**
     * Authentifie un utilisateur à partir de son email et mot de passe.
     * Utilisé dans l'étape de login avant génération du token.
     */
    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (Exception e) {
            throw new RuntimeException("Authentification échouée : " + e.getMessage());
        }
    }

    /**
     * Endpoint de login : vérifie les identifiants, renvoie le user + JWT.
    
    @PostMapping("login")
    public ResponseEntity<UserEntity> login(@RequestBody UserDTO userDTO) {
        // username = email dans ce contexte
        authenticate(userDTO.getUsername(), userDTO.getPassword());
    
        UserEntity loginUser = userService.findUserByEmail(userDTO.getUsername());
    
        HttpHeaders jwtHeaders = getJwtHeader(loginUser);
    
        return new ResponseEntity<>(loginUser, jwtHeaders, HttpStatus.OK);
    }
     */

    @PostMapping("/register")
        public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user) {
            user.setRole(RoleEntity.FORMATEUR); 
            user.setPassword(passwordEncoder.encode(user.getPassword())); 
            UserEntity savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        }

    // pour afficher tout les utilisateurs
    @GetMapping("list")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<UserEntity> addNewUser(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("role") String role,
            @RequestParam("active") String active,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam(value = "idFormation", required = false) Long idFormation) {

        RoleEntity roleEnum = RoleEntity.valueOf(role.toUpperCase());

        UserEntity newUser = userService.addNewUser(
                firstname,
                lastname,
                passwordEncoder.encode(password),
                email,
                roleEnum,
                Boolean.parseBoolean(active),
                true,
                profileImage,
                idFormation
        );

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    // pour  mettre à jour un utilisateur
    @PutMapping("update/{iduser}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("iduser") long iduser, @RequestBody UserEntity updatedUser) {
        UserEntity user = userService.updateUser(
            iduser,
            updatedUser.getFirstname(),
            updatedUser.getLastname(),
            updatedUser.getPassword(),
            updatedUser.getEmail(),
            updatedUser.getRole() != null ? updatedUser.getRole() : RoleEntity.FORMATEUR, 
            true, 
            true, 
            updatedUser.getProfileImageURL(),
            updatedUser.getFormation() != null ? updatedUser.getFormation().getIdFormation() : null
        );
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    

    //pour supprimer un utilisateur
    @DeleteMapping("delete/{iduser}")
    public ResponseEntity<String> deleteUser(@PathVariable("iduser") long iduser) {
        userService.deleteUser(iduser);
        return new ResponseEntity<>(USER_DELETED_SUCCESSFULLY + " ID: " + iduser, HttpStatus.OK);
    }

}
