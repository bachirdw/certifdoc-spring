package com.certifdoc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certifdoc.entity.HistoriqueModificationEntity;
import com.certifdoc.exception.ResourceNotFoundException;

/**
 * Repository (Accès aux données)
 📌 Rôle :
 Interface pour interagir avec la base de données (CRUD).
 Extends souvent JpaRepository ou CrudRepository.
 Fournit automatiquement des méthodes comme save(), findById(), findAll(), delete().
 */

// Interface pour gérer l'accès aux données des historiques de modification
@Repository
public interface HistoriqueModificationRepository extends JpaRepository<HistoriqueModificationEntity, Long> {

    // Méthode personnalisée pour rechercher une modification par description
    Optional<HistoriqueModificationEntity> findByChangeDescription(String description);

    // Méthode pour récupérer un historique par ID avec gestion d'exception
    default HistoriqueModificationEntity getHistoriqueById(Long idHistory) {
        return findById(idHistory)
            .orElseThrow(() -> new ResourceNotFoundException("Historique introuvable avec l'ID : " + idHistory));
    }

}
