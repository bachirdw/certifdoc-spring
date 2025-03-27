package com.certifdoc.dto;

// Objectif : Transport de données API
public class DocumentDTO {
    private Long id;
    private String title;
    private String content;

    // Getters et setters (ou Lombok @Getter/@Setter)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}