package com.certifdoc.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DocumentDTO {
    private Long id;
    private String title;
    private String description;
    private String type;
    private LocalDateTime uploadDate;
    private String version;
    private String storageUrl;
    private Long categorieId; //recupération de l'ID eu lieu de passer l'objet entier pour éviter la récursion infinie
    private Long userId;      // recupération de l'ID eu lieu de passer l'objet entier pour éviter la récursion infinie 
    
    // Optionnel : Si vous voulez inclure le nom de la catégorie
    private String categorieLibelle; 
}