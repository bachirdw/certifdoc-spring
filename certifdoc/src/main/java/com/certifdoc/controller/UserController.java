package com.certifdoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.certifdoc.entity.User;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint pour le login
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User loginUser = userService.findUserByUsername(user.getUsername());
        if (loginUser != null && loginUser.getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>(loginUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // Endpoint pour l'enregistrement d'un nouvel utilisateur
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UsernameExistException, EmailExistException {
        User newUser = userService.register(user.getFirstname(), user.getLastname(), user.getUsername(), user.getEmail(), user.getPassword());
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Endpoint pour récupérer tous les utilisateurs
    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Endpoint pour ajouter un nouvel utilisateur
    @PostMapping("/add")
    public ResponseEntity<User> addNewUser(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("role") String role,
            @RequestParam("active") boolean active,
            @RequestParam("isNotLocked") boolean isNotLocked) {

        User newUser = userService.addNewUser(firstname, lastname, username, password, email, role, active, isNotLocked);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Endpoint pour supprimer un utilisateur
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("Utilisateur supprimé avec succès : " + id, HttpStatus.OK);
    }
}