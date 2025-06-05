package com.certifdoc.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.certifdoc.entity.UserEntity;
import com.certifdoc.entity.RoleEntity; 

public interface UserService {
    List<UserEntity> getUsers();
    UserEntity findUserByLastname(String lastname);
    UserEntity findUserByEmail(String email);
    UserEntity saveUser(UserEntity user);
    UserEntity addNewUser(String firstname, String lastname, String password, String email, RoleEntity role,
                          boolean isActive, boolean isNotLocked, MultipartFile profileImage, Long idFormation);
    UserEntity updateUser(long iduser, String firstname, String lastname, String password, String email, RoleEntity role,
                          boolean isActive, boolean isNotLocked, String profileImageURL, Long idFormation);
    void deleteUser(long iduser);
}
