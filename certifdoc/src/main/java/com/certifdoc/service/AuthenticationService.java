package com.certifdoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.certifdoc.dto.SignInRequest;
import com.certifdoc.dto.SignUpRequest;
import com.certifdoc.entity.User;
import com.certifdoc.repository.IUserRepository;
@Service
public class AuthenticationService {

   @Autowired
    private UserService userService;
    @Autowired
private IUserRepository userRepository;
@Autowired
private PasswordEncoder passwordEncoder;

    public Object signup(SignUpRequest signUpRequest) {
        // créer un nouvel utilisateur à partir de la requête d'inscription
        User user = new User();
        user.setFirstname(signUpRequest.getFirstName());
        user.setLastname(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        user.setPassword(encodedPassword);
        return userService.register(user.getFirstname(), user.getLastname(), user.getEmail());
    }

    public Object signin(SignInRequest signInRequest) {
        // Recherchez l'utilisateur par email
        User user = userRepository.findByEmail(signInRequest.getEmail());
        if (user == null || !passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect.");
        }
        /*if (user == null || !user.getPassword().equals(signInRequest.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect.");
        }:*/
        return user; // Retournez l'utilisateur connecté
    }
    
}
