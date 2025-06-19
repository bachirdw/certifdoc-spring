package com.certifdoc.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "document_keyword") // Évite les conflits avec les mots-clés SQL
public class DocumentKeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocumentKeyword;

    private String name;

    @ManyToOne
    @JoinColumn(name = "idDocument", nullable = false) // Relie le mot-clé à un document
    private DocumentEntity document;
    
    @ManyToOne
    @JoinColumn(name = "idKeyword", nullable = false) // Relie le document à un mot-clé
    private KeywordEntity keyword;

    // pour pouvoir filtrer par catégorie
    @ManyToOne
    @JoinColumn(name = "idCategorie", nullable = false) // Relie le document à une catégorie
    private CategorieEntity categorie;

}