package com.certifdoc.entity;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

@Entity
@Table(name = "role") // Table en base de données
@Data // Génère automatiquement les getters, setters et constructeurs via Lombok
// pour l'instant on déclare Role comme classe et apres sping security on va la declarer comme enum
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role_authorities", joinColumns = @JoinColumn(name = "id_role-aut"))
    @Column(name = "authority")
    private List<String> authorities;

    @ManyToMany(mappedBy = "role")
    private Collection<User> users; // Liste des utilisateurs associés à ce rôle

    // Constructeur par défaut
    public Role() {
    }

    // Constructeur avec paramètres
    public Role(String name, List<String> authorities) {
        this.name = name;
        this.authorities = authorities;
    }
}
