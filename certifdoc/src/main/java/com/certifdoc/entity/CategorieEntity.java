package com.certifdoc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "categorie")
public class CategorieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "idCategorie")
    private Long idCategorie;

    private String libelle;

    //  Relation : une catégorie contient plusieurs documents
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)
    private List<DocumentEntity> documents;
    
}