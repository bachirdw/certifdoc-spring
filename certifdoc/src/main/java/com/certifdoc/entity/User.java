package com.certifdoc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Classe représentant un utilisateur dans l'application.
 * Mappée à la table "user" en base de données.
 */
@Entity
@Table(name = "utilisateur") // Nom de la table en minuscule pour respecter les standards SQL
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long iduser; // Identifiant unique de l'utilisateur

    @Column(name = "firstname", nullable = false)
    private String firstname; // Prénom de l'utilisateur

    @Column(name = "lastname", nullable = false)
    private String lastname; // Nom de l'utilisateur

    @Column(name = "email", unique = true, nullable = false)
    private String email; // Adresse email unique

    @Column(name = "password", nullable = false)
    private String password; // Mot de passe sécurisé

    private Role role;
/* 
    @ManyToOne
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    */

 
    @Column(name = "profile_image_url")
    private String profileImageURL; // URL de l'image de profil

    /**
     * Liste des autorisations associées à l'utilisateur.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private String[] authorities; // Ex: "READ_PRIVILEGE", "EDIT_PRIVILEGE"

    @Column(name = "is_active")
    private boolean isActive = true; // Indique si l'utilisateur est actif

    @Column(name = "is_not_locked", nullable = true)
    private boolean isNotLocked = true; // Indique si le compte est verrouillé ou non

    @Column(name = "last_login"  , nullable = true)
    private Date lastLoginDate; // Date de la dernière connexion

    @Column(name = "join_date", updatable = false , nullable = true)
    private Date joinDate = new Date(); // Date d'inscription (non modifiable)

}
