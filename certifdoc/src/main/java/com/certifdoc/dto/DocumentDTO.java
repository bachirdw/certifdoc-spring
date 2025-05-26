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
    private Long categorieId; // Only ID to avoid recursion
    private Long userId;      // Only ID to avoid recursion
    
    // Optionnel : Si vous voulez inclure le nom de la catégorie
    private String categorieLibelle; 
}