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

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "send_date", nullable = false)
    private Date sendDate;

    //  Une notification appartient à un utilisateur
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "idUser", nullable = false)
    private UserEntity utilisateur;

}