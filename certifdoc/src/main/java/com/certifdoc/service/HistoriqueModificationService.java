package com.certifdoc.service;

import com.certifdoc.entity.HistoriqueModificationEntity;
import com.certifdoc.exception.ResourceNotFoundException;
import com.certifdoc.repository.HistoriqueModificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service (Logique métier)
 📌 Rôle :
 Contient la logique métier de l’application.

 Fait le lien entre le Controller et le Repository.

 Facilite la maintenance, la réutilisation et les tests unitaires.
 */
@Service
public class HistoriqueModificationService {

    @Autowired // pour instancier le repository automatiquement
    private HistoriqueModificationRepository historiqueRepository;

    // Méthode pour récupérer tous les historiques de modification
    public List<HistoriqueModificationEntity> getAllHistoriques() {
        return historiqueRepository.findAll();
    }

    // Méthode pour récupérer un historique par ID
    public HistoriqueModificationEntity getHistoriqueById(Long idHistory) {
        return historiqueRepository.findById(idHistory)
                .orElseThrow(() -> new ResourceNotFoundException("Historique introuvable avec l'ID : " + idHistory));
    }

        // ➕ Méthode pour ajouter un nouvel historique
        public HistoriqueModificationEntity addHistorique(HistoriqueModificationEntity historique) {
            historique.setModificationDate(historique.getModificationDate() != null ? historique.getModificationDate() : new Date());
            return historiqueRepository.save(historique);
        }
    

        // 🔁 Méthode pour mettre à jour un historique existant
        public HistoriqueModificationEntity updateHistorique(Long idHistory, HistoriqueModificationEntity historique) {
            HistoriqueModificationEntity existing = historiqueRepository.findById(idHistory)
                    .orElseThrow(() -> new ResourceNotFoundException("Historique introuvable avec l'ID : " + idHistory));
    
            existing.setModificationDate(historique.getModificationDate());
            existing.setChangeDescription(historique.getChangeDescription());
    
            return historiqueRepository.save(existing);
        }
    
        // ❌ Méthode pour supprimer un historique par ID
        public void deleteHistorique(Long idHistory) {
            HistoriqueModificationEntity historique = historiqueRepository.findById(idHistory)
                    .orElseThrow(() -> new ResourceNotFoundException("Historique introuvable avec l'ID : " + idHistory));
    
            historiqueRepository.delete(historique);
        }
    }