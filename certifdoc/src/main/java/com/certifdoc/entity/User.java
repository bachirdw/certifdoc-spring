package com.certifdoc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import javax.management.relation.Role;
//Classe avec getter et setter et constructeur
@Entity
@Table(name = "user") // Nom de la table en minuscule pour respecter les standards SQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false, unique = true)
    private Long id;// ID de la base de données

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role; // Exemple : ROLE_USER, ROLE_ADMIN

    @Column(name = "profile_image_url")
    private String profileImageURL;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private String[] authorities; // Permissions (ex : "READ_PRIVILEGE", "EDIT_PRIVILEGE")

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true; // Pour activer ou désactiver un utilisateur

    @Column(name = "is_not_locked", nullable = false)
    private boolean isNotLocked = true; // Pour bloquer ou débloquer un utilisateur

    @Column(name = "last_login")
    private Date lastLoginDate;

    @Column(name = "join_date", nullable = false, updatable = false)
    private Date joinDate = new Date();

    

    // Constructeurs par défaut
    public User() {
    }

    // Constructeurs avec tous les attributs
    public User(Long id, String firstname, String lastname, String email, String username, String password, Role role,
            String profileImageURL, String[] authorities, boolean isActive, boolean isNotLocked, Date lastLoginDate,
            Date lastLoginDateDisplay, Date joinDate) {
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


    // Getters et Setters all attributes
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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