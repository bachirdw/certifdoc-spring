package com.certifdoc.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.certifdoc.entity.DocumentEntity;
import com.certifdoc.exception.DocumentNotFoundException;
import com.certifdoc.repository.DocumentRepository;
import com.certifdoc.service.DocumentService;

import java.io.IOException;

/**
 * Controller (API / Interface utilisateur)
📌 Rôle :
Gère les requêtes HTTP (GET, POST, PUT, DELETE).

Appelle le Service pour traiter la requête.

Retourne une réponse HTTP (souvent JSON dans le cas d’une API REST).


 */
@RestController
@RequestMapping("/api/documents")
@CrossOrigin("*")
public class DocumentController {
   
    @Autowired
    private DocumentService documentService;

    //  pour récupérer tous les documents
     @GetMapping
    public List<DocumentEntity> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    //  pour récupérer un document par ID
    @GetMapping("/{idDocument}")
    public DocumentEntity getDocumentById(@PathVariable Long idDocument) {
        return documentService.getDocumentById(idDocument);
    }

     //  pour ajouter un nouveau document
     @PostMapping
     public DocumentEntity addDocument(@RequestBody DocumentEntity document) {
         return documentService.addDocument(document);
     }
    
    @PutMapping("/{idDocument}")
    public ResponseEntity<DocumentEntity> updateDocument(@PathVariable Long idDocument, @RequestBody DocumentEntity updatedDocument) {
        try {
            DocumentEntity document = documentService.updateDocument(idDocument, updatedDocument);
            return ResponseEntity.ok(document);
        } catch (DocumentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

     // pour supprimer un document par ID
    @DeleteMapping("/{idDocument}")
    public ResponseEntity<Void> deleteDocument(@PathVariable(name = "idDocument") Long documentId) {
        try {
            documentService.deleteDocumentById(documentId);
            return ResponseEntity.noContent().build(); // 204 No Content = succès sans retour
        } catch (DocumentNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build(); // 500
        }
    }
  
    // 🔍 Rechercher les documents par catégorie
    @GetMapping("/categorie/{idCategorie}")
    public ResponseEntity<List<DocumentEntity>> getDocumentsByCategorie(@PathVariable Long idCategorie) {
        List<DocumentEntity> docs = documentService.getDocumentsByCategorie(idCategorie);
        return ResponseEntity.ok(docs);
    }

    // 🔍 Rechercher les documents par mot-clé
    @GetMapping("/keyword/{idKeyword}")
    public ResponseEntity<List<DocumentEntity>> getDocumentsByKeyword(@PathVariable Long idKeyword) {
        List<DocumentEntity> docs = documentService.getDocumentsByKeyword(idKeyword);
        return ResponseEntity.ok(docs);
    }


     
     // téléverser un document
     @PostMapping("/upload")
     public ResponseEntity<String> uploadDocument(@RequestPart("file") MultipartFile file) {
         if (file.isEmpty()) return ResponseEntity.badRequest().body("❌ Le fichier est vide !");
     
         try {
             String path = documentService.saveFile(file);
             return ResponseEntity.ok("✅ Fichier téléchargé avec succès à l'emplacement : " + path);
         } catch (RuntimeException e) {
             return ResponseEntity.internalServerError().body("⚠️ Erreur : " + e.getMessage());
         }
     }

}
