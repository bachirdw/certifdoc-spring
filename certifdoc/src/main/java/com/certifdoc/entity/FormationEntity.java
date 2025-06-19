package com.certifdoc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "formation")
@Data
public class FormationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFormation;

    @Column(nullable = false)
    private String name;

    private String description;

      //  Relation : une formation est suivie par plusieurs utilisateurs
      @OneToMany(mappedBy = "formation")
      @JsonIgnoreProperties({"firstname","lastname","email","password","role","profileImageURL","authorities","joinDate","formation","documents","notifications"})
      private List<UserEntity> utilisateurs;

   
}