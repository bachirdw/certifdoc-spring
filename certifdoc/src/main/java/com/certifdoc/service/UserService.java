package com.certifdoc.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.certifdoc.entity.UserEntity;

public interface UserService {
    List<UserEntity> getUsers();
    UserEntity findUserByLastname(String lastname);
    UserEntity findUserByEmail(String email);
    UserEntity addNewUser(String firstname, String lastname, String password, String email, String role, boolean isActive, boolean isNotLocked, MultipartFile profileImage, Long idFormation);
    void deleteUser(long iduser);
    UserEntity updateUser(long iduser, String firstname, String lastname, String password, String email, String role, boolean isActive, boolean isNotLocked, String profileImage);
}