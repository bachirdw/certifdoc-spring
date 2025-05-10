package com.certifdoc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certifdoc.entity.DossierAuditEntity;
import com.certifdoc.exception.DossierAuditNotFoundException;

/**
 *  Repository (Accès aux données)
 * 📌 Rôle :
 * - Interface pour interagir avec la base de données (CRUD).
 * - Extends souvent JpaRepository ou CrudRepository.
 * - Fournit automatiquement des méthodes comme save(), findById(), findAll(), delete().
 */

// Interface pour gérer l'accès aux données des dossiers d'audit
@Repository
public interface DossierAuditRepository extends JpaRepository<DossierAuditEntity, Long> {


    // Méthode pour récupérer un dossier d'audit par ID avec gestion d'exception
    default DossierAuditEntity getDossierById(Long idDossierAudit) {
        return findById(idDossierAudit)
                .orElseThrow(() -> new DossierAuditNotFoundException("Dossier d'audit introuvable avec l'ID : " + idDossierAudit));
    }
}