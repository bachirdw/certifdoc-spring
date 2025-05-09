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

    // 📌 Ajouter un dossier d’audit
    @PostMapping
    public DossierAudit addDossier(@RequestBody DossierAudit dossierAudit) {
        return dossierAuditService.addDossier(dossierAudit);
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
    
    //  Générer un dossier d’audit à partir de documents
    @PostMapping("/generate")
    public ResponseEntity<DossierAudit> generateDossierAudit(@RequestBody DocumentIdListDTO request) {
        DossierAudit dossierAudit = dossierAuditService.generateDossierAudit(request.getDocumentIds());
        return ResponseEntity.ok(dossierAudit);
    }
    //
    @GetMapping("/pdf/download/{idDossierAudit}")
    public void downloadDossierAuditPDF(@PathVariable Long id, HttpServletResponse response) throws IOException {
        dossierAuditService.generateAuditReport(id, response);
    }

}