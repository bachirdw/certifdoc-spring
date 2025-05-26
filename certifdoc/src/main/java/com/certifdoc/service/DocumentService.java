package com.certifdoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import com.certifdoc.entity.DocumentEntity;
import com.certifdoc.entity.HistoriqueModificationEntity;
import com.certifdoc.repository.DocumentRepository;
import com.certifdoc.repository.HistoriqueModificationRepository;

import jakarta.transaction.Transactional;

import com.certifdoc.exception.DocumentNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
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
@Transactional // encapsule les requêtes SQL pour eviter les probleme de lazy loading
public class DocumentService {

    @Autowired // pour instancier le repository automatiquement
    private DocumentRepository documentRepository;

    @Autowired
    private HistoriqueModificationRepository historiqueRepository;

    // Méthode pour récupérer tous les documents
    public List<DocumentEntity> getAllDocuments() {
        try {
            return documentRepository.findAll();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des documents : " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    // Méthode pour récupérer un document par ID
    public DocumentEntity getDocumentById(Long idDocument) {
        return documentRepository.findById(idDocument)
                .orElseThrow(() -> new DocumentNotFoundException("Document introuvable avec l'ID : " + idDocument));
    }  
    // Méthode pour ajouter  un nouveau document
    public DocumentEntity addDocument(DocumentEntity document) {
        return documentRepository.save(document);
    }

    // Méthode pour mettre à jour un document
    public DocumentEntity updateDocument(Long idDocument, DocumentEntity updatedDocument) {
        DocumentEntity existingDocument = documentRepository.findById(idDocument)
                .orElseThrow(() -> new DocumentNotFoundException("Document introuvable avec l'ID : " + idDocument));
    
        existingDocument.setTitle(updatedDocument.getTitle());
        existingDocument.setDescription(updatedDocument.getDescription());
        existingDocument.setCategory(updatedDocument.getCategory());
        existingDocument.setType(updatedDocument.getType());
        existingDocument.setVersion(updatedDocument.getVersion());
        existingDocument.setStorageUrl(updatedDocument.getStorageUrl());
        existingDocument.setFileSize(updatedDocument.getFileSize());
        existingDocument.setFileHash(updatedDocument.getFileHash());
        existingDocument.setFilePath(updatedDocument.getFilePath());
    
        DocumentEntity savedDocument = documentRepository.save(existingDocument);
    
        // ✅ Enregistrement de l’historique
        HistoriqueModificationEntity historique = new HistoriqueModificationEntity();
        historique.setModificationDate(new Date());
        historique.setChangeDescription("Mise à jour du document : " + savedDocument.getTitle());
        historique.setDocument(savedDocument);
        historiqueRepository.save(historique);
    
        return savedDocument;
    }

       //recuperer tous les documents d’une catégorie
    public List<DocumentEntity> getDocumentsByCategorie(Long idCategorie) {
        return documentRepository.findByCategorie_IdCategorie(idCategorie);
    }
    // Récupérer tous les documents associés à un mot-clé.
    public List<DocumentEntity> getDocumentsByKeyword(Long idKeyword) {
        return documentRepository.findByKeywords_IdKeyword(idKeyword);
    }
    
    // Méthode pour supprimer un document par ID
    public void deleteDocumentById(Long idDocument) {
        DocumentEntity document = documentRepository.findById(idDocument)
                .orElseThrow(() -> new RuntimeException("Document introuvable avec l'ID : " + idDocument));
        
        documentRepository.delete(document);
    }
    

    // Méthode pour enregistrer un fichier et stocker les fichiers sur le disque
    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Le fichier est vide !");
        }
    
        try {
            String fileName = file.getOriginalFilename();
            String storagePath = System.getProperty("user.dir") + "/uploads/" + fileName;
            File destinationFile = new File(storagePath);
            file.transferTo(destinationFile);
    
            // Sauvegarde en base
            DocumentEntity doc = new DocumentEntity();
            doc.setTitle(fileName); // Ou une autre logique si besoin
            doc.setFilePath(storagePath);
            documentRepository.save(doc);
    
            return storagePath;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du stockage du fichier", e);
        }
    }
    
    
}