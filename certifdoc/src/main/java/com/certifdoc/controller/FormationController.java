package com.certifdoc.controller;

import com.certifdoc.entity.FormationEntity;
import com.certifdoc.service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formations")
public class FormationController {

    @Autowired
    private FormationService formationService;

    // Récupérer toutes les formations
    @GetMapping
    public List<FormationEntity> getAllFormations() {
        return formationService.getAllFormations();
    }

    // Récupérer une formation par ID
    @GetMapping("/{idFormation}")
    public FormationEntity getFormationById(@PathVariable Long id) {
        return formationService.getFormationById(id);
    }

    // Ajouter une nouvelle formation
    @PostMapping
    public FormationEntity addFormation(@RequestBody FormationEntity formation) {
        return formationService.addFormation(formation);
    }

    // Mettre à jour une formation existante
    @PutMapping("/{idFormation}")
    public FormationEntity updateFormation(@PathVariable Long id, @RequestBody FormationEntity formation) {
        return formationService.updateFormation(id, formation);
    }

    // Supprimer une formation
    @DeleteMapping("/{idFormation}")
    public void deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
    }
}