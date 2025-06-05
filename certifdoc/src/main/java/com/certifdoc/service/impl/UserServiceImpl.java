package com.certifdoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.certifdoc.entity.UserEntity;
import com.certifdoc.entity.FormationEntity;
import com.certifdoc.entity.RoleEntity;
import com.certifdoc.repository.UserRepository;
import com.certifdoc.repository.FormationRepository;
import com.certifdoc.service.UserService;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FormationRepository formationRepository;

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
public UserEntity findUserByLastname(String lastname) {
    return userRepository.findByLastname(lastname).orElse(null);
}

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

@Override
public UserEntity addNewUser(String firstname, String lastname, String password, String email, RoleEntity role,
         boolean isActive, boolean isNotLocked, MultipartFile profileImage, Long idFormation) {
    
    UserEntity user = new UserEntity();
    user.setFirstname(firstname);
    user.setLastname(lastname);
    user.setPassword(password);
    user.setEmail(email);
    user.setRole(role);
    user.setActive(isActive);
    user.setNotLocked(isNotLocked);

    // Traitement de l'image
    if (profileImage != null && !profileImage.isEmpty()) {
        try {
            // Exemple de traitement d'image
            String fileName = profileImage.getOriginalFilename();
            user.setProfileImageURL(fileName); // à adapter à ta logique métier
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du traitement de l'image : " + e.getMessage());
        }
    }

    // Lier la formation si elle existe
    if (idFormation != null) {
        Optional<FormationEntity> formation = formationRepository.findById(idFormation);
        formation.ifPresent(user::setFormation);
    }

    return userRepository.save(user); 
}



    @Override
    public UserEntity updateUser(long iduser, String firstname, String lastname, String password, String email, RoleEntity role,
                                 boolean isActive, boolean isNotLocked, String profileImage, Long idFormation) {
        Optional<UserEntity> optionalUser = userRepository.findById(iduser);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Utilisateur avec l'ID " + iduser + " non trouvé !");
        }
        UserEntity user = optionalUser.get();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);
        user.setActive(isActive);
        user.setNotLocked(isNotLocked);
    
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(long iduser) {
        if (userRepository.existsById(iduser)) {
            userRepository.deleteById(iduser);
        } else {
            throw new RuntimeException("Utilisateur avec l'ID " + iduser + " non trouvé !");
        }
    }
}
