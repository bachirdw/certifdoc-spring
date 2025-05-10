package com.certifdoc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "log_entity") // Évite les conflits SQL
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLog;

    private String action;
    private Date timestamp;

    /*@ManyToOne
    @JoinColumn(name = "idUser")
    private UserEntity userEntity; // Utilisateur associé à l'action
*/
}