package com.certifdoc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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