package com.certifdoc.entity;

import jakarta.persistence.*;

@Entity

@Table(name = "keyword_entity") // Évite tout conflit avec un mot-clé SQL
public class KeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKeyword;

    private String name;

    // Getters et Setters
    public Long getIdKeyword() {
        return idKeyword;
    }

    public void setIdKeyword(Long idKeyword) {
        this.idKeyword = idKeyword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}