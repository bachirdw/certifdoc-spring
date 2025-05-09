
package com.certifdoc.service.impl;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.certifdoc.entity.Role;
import com.certifdoc.entity.User;
import com.certifdoc.repository.UserRepository;
import com.certifdoc.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByLastname(String lastname) {
        return userRepository.findByFirstname(lastname);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByLastname(email);
    }

//pour ajouter un utilisateur
    @Override
    public User addNewUser(String firstname, String lastname, String password, String email, String role,
            boolean isActive, boolean isNotLocked, MultipartFile profileImage) {
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(Role.valueOf(role.toUpperCase())); // Vérification du rôle
        user.setActive(isActive);
        user.setNotLocked(isNotLocked);
        return userRepository.save(user);
    }

// pour mettre ajouter un utilisateur
    @Override
    public User updateUser(long iduser, String firstname, String lastname, String password, String email, String role,
            boolean isActive, boolean isNotLocked, String profileImage) {
        Optional<User> optionalUser = userRepository.findById(iduser);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Utilisateur avec l'ID " + iduser + " non trouvé !");
        }
        User user = optionalUser.get();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(Role.valueOf(role.toUpperCase()));
        user.setActive(isActive);
        user.setNotLocked(isNotLocked);
        return userRepository.save(user);
    }

    //pour supprimer un utilisateur
    @Override
    public void deleteUser(long iduser) {
        if (userRepository.existsById(iduser)) {
            userRepository.deleteById(iduser);
        } else {
            throw new RuntimeException("Utilisateur avec l'ID " + iduser + " non trouvé !");
        }
    }
}
