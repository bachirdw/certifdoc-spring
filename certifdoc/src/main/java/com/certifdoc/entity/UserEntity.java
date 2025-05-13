package com.certifdoc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "utilisateur")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser", nullable = false, unique = true)
    private Long idUser;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEntity role;

    @Column(name = "profile_image_url")
    private String profileImageURL;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "Userid"))
    @Column(name = "authority")
    private String[] authorities;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "is_not_locked")
    private boolean isNotLocked = true;

    @Column(name = "last_login")
    private Date lastLoginDate;

    @Column(name = "join_date", updatable = false)
    private Date joinDate = new Date();

    //  Lien vers la formation (n utilisateurs → 1 formation)
    @ManyToOne
    @JoinColumn(name = "idFormation")
    private FormationEntity formation;

    //  Documents créés par l'utilisateur
    @OneToMany(mappedBy = "utilisateur")
    private List<DocumentEntity> documents;

    //  Notifications reçues
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<NotificationEntity> notifications;

    // Logs des actions de l'utilisateur
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<LogEntity> logs;
}
