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

    @Column(nullable = false)
    private String action;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    // Relation : chaque log est associé à un utilisateur
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "idUser")
    private UserEntity utilisateur;
 }