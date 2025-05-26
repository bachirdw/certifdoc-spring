package com.certifdoc.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
on isole les service pour les test unitaire

1. Entity (ou Modèle de données)
📌 Rôle :
Représente une table de la base de données.

Chaque instance d’une entité représente une ligne dans la table.

Sert à la persistance des données.
 */

@Entity
@Table(name = "DOCUMENT")
// c'est lombok qui va générer les getters et setters
@Data
// pour crer le constructeur sans argument sans l'écris ligne par ligne
@NoArgsConstructor
// pour crer le constructeur avec tous les arguments sans l'écris ligne par ligne
@AllArgsConstructor
//pour crer des objets automatiquement et les utilisé quand  on a besoin
@Builder
@Transactional

public class DocumentEntity {

    // pour dire que id est la clé primaire
    @Id
    // pour dire que id est auto incrémenté = génération automatique
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDocument")
    private Long idDocument;

    // nullable = false pour dire que le titre est obligatoire
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "type")
    private String type;

    @Column(name = "upload_date", nullable = true, updatable = true)// true pour l'instant aprés changer false
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate = new Date();

    @Column(name = "version")
    private String version;

    @Column(name = "storage_url")
    private String storageUrl;

    @Column(name = "file_size")
    private Integer fileSize;

    @Column(name = "file_hash")
    private String fileHash;

    @Column(name = "file_path")
    private String filePath;


    //  Relation  chaque document appartient à un utilisateur
    @ManyToOne
    @JsonBackReference // pour éviter la récursivité infinie
    @JoinColumn(name = "idUser", nullable = true)
    private UserEntity utilisateur;

    //  Relation : chaque document appartient à une catégorie
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "idCategorie")
    //private CategorieEntity categorie;

    @ManyToOne
    @JoinColumn(name = "idCategorie")
    @JsonIgnore // Ignore complètement la sérialisation de la catégorie
    private CategorieEntity categorie;
    
    // Ajoutez cette méthode pour exposer seulement l'ID de la catégorie
    @JsonProperty("categorieId")
    public Long getCategorieId() {
        return categorie != null ? categorie.getIdCategorie() : null;
    }

    //  un document peut avoir plusieurs audits
    @ManyToOne
    @JoinColumn(name = "idDossierAudit")
    private DossierAuditEntity dossierAudit;

    //  Historique des modifications (One-to-Many)
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<HistoriqueModificationEntity> historiqueModifications;

    // Relation Many-to-Many avec mots-clés via table de jointure
    @ManyToMany
    @JoinTable(
        name = "DOCUMENT_KEYWORD",
        joinColumns = @JoinColumn(name = "idDocument"),
        inverseJoinColumns = @JoinColumn(name = "idKeyword")
    )
    private List<KeywordEntity> keywords;


}

