package com.certifdoc.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.certifdoc.entity.Document;
import com.certifdoc.service.DocumentService;
/**
 * Controller (API / Interface utilisateur)
📌 Rôle :
Gère les requêtes HTTP (GET, POST, PUT, DELETE).

Appelle le Service pour traiter la requête.

Retourne une réponse HTTP (souvent JSON dans le cas d’une API REST).


 */
@RestController
@RequestMapping("/api/documents")
public class DocumentController {
   
    @Autowired
    private DocumentService documentService;

    //  pour récupérer tous les documents
    @GetMapping
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    //  pour récupérer un document par ID
    @GetMapping("/{id}")
    public Document getDocumentById(@PathVariable Long id) {
        return documentService.getDocumentById(id);
    }

     //  pour ajouter un nouveau document
     @PostMapping
     public Document addDocument(@RequestBody Document document) {
         return documentService.addDocument(document);
     }
     // voir ytb 
         @PostMapping("/upload")
    public String uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            // Logique pour sauvegarder le fichier
            String fileName = file.getOriginalFilename();
            String storagePath = "C:/uploads/" + fileName; // Chemin où le fichier sera stocké
            file.transferTo(new File(storagePath));
    
            // Retourner un message de succès
            return "Fichier téléchargé avec succès : " + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du téléchargement du fichier", e);
        }
    }
}
