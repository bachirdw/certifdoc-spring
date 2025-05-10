package com.certifdoc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "notification_entity") // ✅ Évite les conflits avec le mot-clé SQL
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;

    private String message;
    private String type;
    private Date sendDate;

 /*    @ManyToOne
    @JoinColumn(name = "idUser") // Relie la notification à un utilisateur
    private UserEntity userEntity; // Utilisateur associé à la notification

    */
}