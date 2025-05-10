package com.certifdoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.certifdoc.dto.DocumentIdListDTO;
import com.certifdoc.entity.DossierAudit;
import com.certifdoc.exception.DossierAuditNotFoundException;
import com.certifdoc.service.DossierAuditService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Controller (API / Interface utilisateur)
 * 📌 Rôle :
 * - Gère les requêtes HTTP (GET, POST, PUT, DELETE).
 * - Appelle le Service pour traiter la requête.
 * - Retourne une réponse HTTP (souvent JSON dans le cas d’une API REST).
 */

@RestController
@RequestMapping("/api/dossiers-audit")
public class DossierAuditController {

    @Autowired
    private DossierAuditService dossierAuditService;

    // 📌 Récupérer tous les dossiers d’audit
    @GetMapping
    public List<DossierAudit> getAllDossiers() {
        return dossierAuditService.getAllDossiers();
    }

    // 📌 Récupérer un dossier d’audit par ID
    @GetMapping("/{idDossierAudit}")
    public ResponseEntity<DossierAudit> getDossierById(@PathVariable Long idDossierAudit) {
        try {
            DossierAudit dossier = dossierAuditService.getDossierById(idDossierAudit);
            return ResponseEntity.ok(dossier)   ;
        } catch (DossierAuditNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    //  Mettre à jour un dossier d’audit
    @PutMapping("/{idDossierAudit}")
    public ResponseEntity<DossierAudit> updateDossier(@PathVariable Long idDossierAudit, @RequestBody DossierAudit updatedDossier) {
        try {
            DossierAudit dossier = dossierAuditService.updateDossier(idDossierAudit, updatedDossier);
            return ResponseEntity.ok(dossier);
        } catch (DossierAuditNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //  Supprimer un dossier d’audit
    @DeleteMapping("/{idDossierAudit}")
    public ResponseEntity<Void> deleteDossier(@PathVariable Long idDossierAudit) {
        try {
            dossierAuditService.deleteDossierById(idDossierAudit);
            return ResponseEntity.noContent().build();
        } catch (DossierAuditNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    //  Générer un dossier d’audit
    @PostMapping("/generate")
    public ResponseEntity<?> generateDossierAudit(@RequestBody DocumentIdListDTO request) {
        List<Long> documentIds = request.getDocumentIds();
    
        // 🛑 Vérification basique
        if (documentIds == null || documentIds.isEmpty()) {
            return ResponseEntity.badRequest().body("Aucun document sélectionné.");
        }
    
        try {
            //  Étape 1 : Vérification de complétude
            boolean isComplete = dossierAuditService.verifyDocumentCompleteness(documentIds);
    
            if (!isComplete) {
                return ResponseEntity.badRequest().body("❌ Documents manquants. Veuillez compléter le dossier.");
            }
    
            //  Étape 2 : Création d’un objet DossierAudit vide
            DossierAudit dossier = new DossierAudit();
            // Étape 3 : Génération complète (sauvegarde, PDF, URL)
            DossierAudit generatedDossier = dossierAuditService.generateDossierAuditWithPdfInDb(dossier, documentIds);
            return ResponseEntity.ok(generatedDossier);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de la génération du dossier : " + e.getMessage());
        }
    }
    
    //Exposer une route de téléchargement du PDF dans le DossierAuditController
    @GetMapping("/{idDossierAudit}/download")
public ResponseEntity<byte[]> downloadPdf(@PathVariable Long idDossierAudit) {
    try {
        DossierAudit dossier = dossierAuditService.getDossierById(idDossierAudit);
        byte[] pdfContent = dossier.getPdfContent();

        if (pdfContent == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=DossierAudit_" + dossier.getIdDossierAudit() + ".pdf")
                .header("Content-Type", "application/pdf")
                .body(pdfContent);
    } catch (DossierAuditNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}


}