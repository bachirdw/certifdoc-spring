package com.certifdoc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.certifdoc.entity.Document;
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
public interface DocumentRepository extends JpaRepository<Document, Long> {
    // Méthode personnalisée pour trouver un document par titre
    Optional<Document> findByTitle(String title);

    // Méthode pour récupérer un document par ID avec gestion d'exception
    default Document getDocumentById(Long id) {
        return findById(id)
            .orElseThrow(() -> new DocumentNotFoundException("Document introuvable avec l'ID : " + id));
    }
}