package com.certifdoc.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategorieDTO {
    private Long id;
    private String libelle;
    private List<Long> documentIds; // Only IDs to keep it lightweight
}