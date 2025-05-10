package com.certifdoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.certifdoc.entity.DocumentEntity;
import com.certifdoc.repository.DocumentRepository;
import com.certifdoc.exception.DocumentNotFoundException;

import java.io.File;
import java.io.IOException;
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

    private DocumentRepository documentRepository;

    // Méthode pour récupérer tous les documents
    public List<DocumentEntity> getAllDocuments() {
        return documentRepository.findAll();
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

    return documentRepository.save(existingDocument);
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