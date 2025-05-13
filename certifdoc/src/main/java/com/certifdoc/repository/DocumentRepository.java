package com.certifdoc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certifdoc.entity.DocumentEntity;
import com.certifdoc.exception.DocumentNotFoundException;
/**
 *  Repository (Accès aux données)
📌 Rôle :
Interface pour interagir avec la base de données (CRUD).
Extends souvent JpaRepository ou CrudRepository.
Fournit automatiquement des méthodes comme save(), findById(), findAll(), delete().
 */

// Interface pour gérer l'accès aux données des documents
@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    // Méthode personnalisée pour trouver un document par titre
    Optional<DocumentEntity> findByTitle(String title);

    // Méthode pour récupérer un document par ID avec gestion d'exception
    default DocumentEntity getDocumentById(Long idDocument) {
        return findById(idDocument)
            .orElseThrow(() -> new DocumentNotFoundException("Document introuvable avec l'ID : " + idDocument));
    }

 
    // Trouver tous les documents par catégorie (assure-toi que `categorie` est bien une entité reliée)
    List<DocumentEntity> findByCategorie_IdCategorie(Long idCategorie);

    
    // Trouver tous les documents liés à un mot-clé
    List<DocumentEntity> findByKeywords_IdKeyword(Long idKeyword);


}