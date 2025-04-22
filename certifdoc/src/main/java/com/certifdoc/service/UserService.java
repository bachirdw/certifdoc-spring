package com.certifdoc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.certifdoc.entity.Role;
import com.certifdoc.entity.User;
import com.certifdoc.repository.IUserRepository;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public User register(String firstName, String lastName, String email) {
        User user = new User();
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setEmail(email);
        // Ajoutez d'autres champs nécessaires, comme le rôle par défaut
        user.setRole(new Role("ROLE_USER", List.of("READ_PRIVILEGE")));
        return userRepository.save(user); // si le user n'est pas trouver 
    }
// pour gerer tout les utilisateurs
    public List<User> getUsers() {
            // methode pour récupérer tous les utilisateurs
        return userRepository.findAll();
    }

    public User findUserByLastname(String lastname) {
        return userRepository.findByFirstname(lastname); // Assurez-vous que cette méthode correspond à votre logique
    }

    public User findUserByEmail(String email) {
        return userRepository.findByLastname(email); // Ajustez selon votre logique
    }

    public User addNewUser(String firstname, String lastname, String password, String email, String role,
            boolean isActive, boolean isNotLocked, MultipartFile profileImage) {
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(new Role(role, List.of("READ_PRIVILEGE"))); // Exemple de rôle
        user.setActive(isActive);
        user.setNotLocked(isNotLocked);
        // Ajoutez la logique pour gérer l'image de profil si nécessaire
        return userRepository.save(user);
    }
// pour suprimer une personne 
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}