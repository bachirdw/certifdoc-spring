package com.certifdoc.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Classe représentant un utilisateur dans l'application.
 * Mappée à la table "user" en base de données.
 */
@Entity
@Table(name = "utilisateur") // Nom de la table en minuscule pour respecter les standards SQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long id; // Identifiant unique de l'utilisateur

    @Column(name = "firstname", nullable = false)
    private String firstname; // Prénom de l'utilisateur

    @Column(name = "lastname", nullable = false)
    private String lastname; // Nom de l'utilisateur

    @Column(name = "email", unique = true, nullable = false)
    private String email; // Adresse email unique

    @Column(name = "username", unique = true, nullable = false)
    private String username; // Nom d'utilisateur unique

    @Column(name = "password", nullable = false)
    private String password; // Mot de passe sécurisé

    /**
     * Relation Many-to-One entre User et Role.
     * Un utilisateur a UN rôle, mais un rôle peut être attribué à plusieurs utilisateurs.
     */
    // @ManyToOne
    // @JoinColumn(name = "id_role-user", nullable = false)
    // private Role role; // Rôle de l'utilisateur (ex: ROLE_USER, ROLE_ADMIN)

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> role;

    @Column(name = "profile_image_url")
    private String profileImageURL; // URL de l'image de profil

    /**
     * Liste des autorisations associées à l'utilisateur.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private String[] authorities; // Ex: "READ_PRIVILEGE", "EDIT_PRIVILEGE"

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true; // Indique si l'utilisateur est actif

    @Column(name = "is_not_locked", nullable = false)
    private boolean isNotLocked = true; // Indique si le compte est verrouillé ou non

    @Column(name = "last_login")
    private Date lastLoginDate; // Date de la dernière connexion

    @Column(name = "join_date", nullable = false, updatable = false)
    private Date joinDate = new Date(); // Date d'inscription (non modifiable)

    /**
     * Constructeur par défaut (nécessaire pour JPA).
     */
    public User() {}

    /**
     * Constructeur avec tous les attributs.
     */
    public User(Long id, String firstname, String lastname, String email, String username, String password, Collection<Role> role,
                String profileImageURL, String[] authorities, boolean isActive, boolean isNotLocked, Date lastLoginDate,
                Date joinDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.profileImageURL = profileImageURL;
        this.authorities = authorities;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
        this.lastLoginDate = lastLoginDate;
        this.joinDate = joinDate;
    }
// Getters et Setters pour chaque attribut
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRole() {
        return role;
    }

    public void setRole( Collection<Role> role) {
        this.role = role;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isNotLocked() {
        return isNotLocked;
    }

    public void setNotLocked(boolean isNotLocked) {
        this.isNotLocked = isNotLocked;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }


}
