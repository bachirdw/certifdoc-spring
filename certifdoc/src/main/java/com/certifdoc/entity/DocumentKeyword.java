package com.certifdoc.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "document_keyword") // Évite les conflits avec les mots-clés SQL
public class DocumentKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocumentKeyword;

    private String name;

    @ManyToOne
    @JoinColumn(name = "idDocument") // Relie le mot-clé à un document
    private DocumentEntity document;
}