package com.certifdoc.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long idUser;
    private String username; // definir le username comme email
    private String password;
    private String lastLoginDate;
    private String joinDate;
    private Long idFormation;
    private String formationName;
}