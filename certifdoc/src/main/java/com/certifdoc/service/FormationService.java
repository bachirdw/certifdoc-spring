package com.certifdoc.service;

import com.certifdoc.entity.FormationEntity;
import com.certifdoc.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormationService {

    @Autowired
    private FormationRepository formationRepository;

    // Récupérer toutes les formations
    public List<FormationEntity> getAllFormations() {
        return formationRepository.findAll();
    }

    // Récupérer une formation par ID
    public FormationEntity getFormationById(Long idFormation) {
        return formationRepository.findById(idFormation)
                .orElseThrow(() -> new RuntimeException("❌ Formation introuvable avec l'ID : " + idFormation));
    }

    // Ajouter une nouvelle formation
    public FormationEntity addFormation(FormationEntity formation) {
        return formationRepository.save(formation);
    }

    // Mettre à jour une formation existante
    public FormationEntity updateFormation(Long idFormation, FormationEntity updatedFormation) {
        FormationEntity existingFormation = getFormationById(idFormation);
        existingFormation.setName(updatedFormation.getName());
        existingFormation.setDescription(updatedFormation.getDescription());
        return formationRepository.save(existingFormation);
    }

    // Supprimer une formation
    public void deleteFormation(Long idFormation) {
        FormationEntity formation = getFormationById(idFormation);
        formationRepository.delete(formation);
    }
}