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

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

      //  Relation : une formation est suivie par plusieurs utilisateurs
      @OneToMany(mappedBy = "formation")
      private List<UserEntity> utilisateurs;

   
}