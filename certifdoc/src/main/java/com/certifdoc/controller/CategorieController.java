package com.certifdoc.controller;

import com.certifdoc.entity.CategorieEntity;
import com.certifdoc.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    // Récupérer toutes les catégories
    @GetMapping
    public List<CategorieEntity> getAllCategories() {
        return categorieService.getAllCategories();
    }

    // Récupérer une catégorie par ID
    @GetMapping("/{id}")
    public CategorieEntity getCategorieById(@PathVariable Long id) {
        return categorieService.getCategorieById(id);
    }

    // Ajouter une nouvelle catégorie
    @PostMapping
    public CategorieEntity addCategorie(@RequestBody CategorieEntity categorie) {
        return categorieService.addCategorie(categorie);
    }
}