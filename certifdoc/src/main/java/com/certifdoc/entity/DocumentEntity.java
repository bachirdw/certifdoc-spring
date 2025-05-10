package com.certifdoc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
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

    @ManyToOne(optional = true) // Un document peut exister sans audit
@JoinColumn(name = "dossier_audit_id", nullable = true)
private DossierAuditEntity dossierAudit;

}