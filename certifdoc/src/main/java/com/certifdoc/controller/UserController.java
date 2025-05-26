package com.certifdoc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.certifdoc.entity.UserEntity;
import com.certifdoc.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";

    @Autowired
    private UserService userService;

    // pour afficher tout les utilisateurs
    @GetMapping("list")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //pour ajouter un utilisateur
    @PostMapping("add")
    public ResponseEntity<UserEntity> addNewUser(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("role") String role,
            @RequestParam("active") String active,
            @RequestParam("isNotLocked") String isNotLocked,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam(value = "idFormation", required = false) Long idFormation) {

        UserEntity newUser = userService.addNewUser(firstname, lastname, password, email, role,
                Boolean.parseBoolean(active), Boolean.parseBoolean(isNotLocked), profileImage, idFormation);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    // pour  mettre à jour un utilisateur
   @PutMapping("maj/{iduser}")
public ResponseEntity<UserEntity> updateUser(@PathVariable("iduser") long iduser, @RequestBody UserEntity updatedUser) {
    UserEntity user = userService.updateUser(
        iduser,
        updatedUser.getFirstname(),
        updatedUser.getLastname(),
        updatedUser.getPassword(),
        updatedUser.getEmail(),
        updatedUser.getRole() != null ? updatedUser.getRole().name() : null,
        updatedUser.isActive(),
        updatedUser.isNotLocked(),
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
