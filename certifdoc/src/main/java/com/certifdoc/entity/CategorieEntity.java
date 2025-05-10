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
    private Long idCategorie;

    private String libelle;

   /*  @OneToMany(mappedBy = "categorie")
    private List<DocumentEntity> documents;
*/
    
}