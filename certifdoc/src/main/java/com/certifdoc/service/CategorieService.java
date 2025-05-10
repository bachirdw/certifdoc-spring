package com.certifdoc.service;

import com.certifdoc.entity.CategorieEntity;
import com.certifdoc.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    // Récupérer toutes les catégories
    public List<CategorieEntity> getAllCategories() {
        return categorieRepository.findAll();
    }

    // Récupérer une catégorie par ID
    public CategorieEntity getCategorieById(Long idCategorie) {
        return categorieRepository.findById(idCategorie)
                .orElseThrow(() -> new RuntimeException("❌ Catégorie introuvable avec l'ID : " + idCategorie));
    }

    // Ajouter une nouvelle catégorie
    public CategorieEntity addCategorie(CategorieEntity categorie) {
        return categorieRepository.save(categorie);
    }

    // Mettre à jour une catégorie existante
    public CategorieEntity updateCategorie(Long idCategorie, CategorieEntity updatedCategorie) {
        CategorieEntity existingCategorie = getCategorieById(idCategorie);
        existingCategorie.setLibelle(updatedCategorie.getLibelle());
        return categorieRepository.save(existingCategorie);
    }

    // Supprimer une catégorie
    public void deleteCategorie(Long idCategorie) {
        CategorieEntity categorie = getCategorieById(idCategorie);
        categorieRepository.delete(categorie);
    }
}