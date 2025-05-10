package com.certifdoc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "formation")
@Data
public class FormationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFormation;

    private String name;
    private String description;

   /* @OneToMany(mappedBy = "formation")
    private List<DocumentEntity> documents;
*/
   
}