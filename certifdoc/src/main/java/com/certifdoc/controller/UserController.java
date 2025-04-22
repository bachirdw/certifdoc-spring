package com.certifdoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.certifdoc.entity.User;
import com.certifdoc.service.UserService;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin("*")
public class UserController {

    private static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";

    @Autowired
    private UserService userService;

    /**
     * Login simplifié (pas sécurisé, à modifier pour production)
     */
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User existingUser = userService.findUserByEmail(user.getEmail());

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User newUser = userService.register(user.getFirstname(), user.getLastname(), user.getEmail());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping("list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<User> addNewUser(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("role") String role,
            @RequestParam("active") String active,
            @RequestParam("isNotLocked") String isNotLocked,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {

        User newUser = userService.addNewUser(firstname, lastname, password, email, role,
                Boolean.parseBoolean(active), Boolean.parseBoolean(isNotLocked), profileImage);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(USER_DELETED_SUCCESSFULLY + " ID: " + id, HttpStatus.OK);
    }
}
