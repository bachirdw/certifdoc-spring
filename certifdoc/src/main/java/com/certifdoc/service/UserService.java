package com.certifdoc.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.certifdoc.entity.User;

public interface UserService {
    List<User> getUsers();
    User findUserByLastname(String lastname);
    User findUserByEmail(String email);
    User addNewUser(String firstname, String lastname, String password, String email, String role, boolean isActive, boolean isNotLocked, MultipartFile profileImage);
    void deleteUser(long iduser);
    User updateUser(long iduser, String firstname, String lastname, String password, String email, String role, boolean isActive, boolean isNotLocked, String profileImage);
}