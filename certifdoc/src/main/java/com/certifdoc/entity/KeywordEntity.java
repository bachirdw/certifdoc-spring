package com.certifdoc.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "keyword_entity") // Évite tout conflit avec un mot-clé SQL
public class KeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKeyword;

    private String name;

    // Relation Many-to-Many avec DocumentEntity via table de jointure
    @ManyToMany(mappedBy = "keywords")
    private List<DocumentEntity> documents;
}

