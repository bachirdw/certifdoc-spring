package com.certifdoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.certifdoc.entity.Document;
import com.certifdoc.repository.IDocumentRepository;
import com.certifdoc.exception.DocumentNotFoundException;

import java.util.List;
/**
 * Service (Logique métier)
📌 Rôle :
Contient la logique métier de l’application.

Fait le lien entre le Controller et le Repository.

Facilite la maintenance, la réutilisation et les tests unitaires.
 */
// anote la classe service
@Service
public class DocumentService {

    @Autowired // pour instancier le repository automatiquement
    private IDocumentRepository documentRepository;
    // Méthode pour récupérer tous les documents
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    // Méthode pour récupérer un document par ID
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document introuvable avec l'ID : " + id));
    }  
    // Méthode pour ajouter  un nouveau document
    public Document addDocument(Document document) {
        return documentRepository.save(document);
    }

    
}