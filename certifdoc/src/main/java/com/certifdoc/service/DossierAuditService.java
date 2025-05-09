package com.certifdoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.certifdoc.entity.Document;
import com.certifdoc.entity.DossierAudit;
import com.certifdoc.repository.DocumentRepository;
import com.certifdoc.repository.DossierAuditRepository;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

import com.certifdoc.exception.DossierAuditNotFoundException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Service (Logique métier)
 * 📌 Rôle :
 * - Contient la logique métier des dossiers d’audit.
 * - Fait le lien entre le Controller et le Repository.
 * - Facilite la maintenance, la réutilisation et les tests unitaires.
 */
@Service
public class DossierAuditService {

    @Autowired // Injection automatique du repository
    private DossierAuditRepository dossierAuditRepository;

    @Autowired
    private DocumentRepository documentRepository;

    // 📌 Méthode pour récupérer tous les dossiers d’audit
    public List<DossierAudit> getAllDossiers() {
        return dossierAuditRepository.findAll();
    }

    // 📌 Méthode pour récupérer un dossier d’audit par ID
    public DossierAudit getDossierById(Long idDossierAudit) {
        return dossierAuditRepository.findById(idDossierAudit)
                .orElseThrow(() -> new DossierAuditNotFoundException("❌ Dossier introuvable avec l'ID : " + idDossierAudit));
    }

    // 📌 Méthode pour ajouter un nouveau dossier d’audit
    public DossierAudit addDossier(DossierAudit dossierAudit) {
        return dossierAuditRepository.save(dossierAudit);
    }

    // 📌 Méthode pour mettre à jour un dossier d’audit
    public DossierAudit updateDossier(Long idDossierAudit, DossierAudit updatedDossier) {
        DossierAudit existingDossier = dossierAuditRepository.findById(idDossierAudit)
                .orElseThrow(() -> new DossierAuditNotFoundException("❌ Dossier introuvable avec l'ID : " + idDossierAudit));

        existingDossier.setStatut(updatedDossier.getStatut());
        existingDossier.setUrlPdf(updatedDossier.getUrlPdf());

        return dossierAuditRepository.save(existingDossier);
    }

    // 📌 Méthode pour supprimer un dossier d’audit par ID
    public void deleteDossierById(Long id) {
        DossierAudit dossierAudit = dossierAuditRepository.findById(id)
                .orElseThrow(() -> new DossierAuditNotFoundException("❌ Dossier introuvable avec l'ID : " + id));

        dossierAuditRepository.delete(dossierAudit);
    }

    // méthode pour générer le pdf et retourner l'url
    public DossierAudit generateDossierAudit(List<Long> documentIds) {
        List<Document> documents = documentRepository.findAllById(documentIds);
        if (documents.isEmpty()) {
            throw new RuntimeException("❌ Aucun document trouvé.");
        }
    
        boolean complet = areAllRequiredDocumentsPresent(documents);
    
        DossierAudit dossierAudit = new DossierAudit();
        dossierAudit.setCreationDate(new Date());
        dossierAudit.setDocuments(documents);
    
        if (complet) {
            dossierAudit.setStatut("COMPLET");
            try {
                String url = generatePdfAndGetUrl(dossierAudit);
                dossierAudit.setUrlPdf(url);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la génération du PDF", e);
            }
        } else {
            dossierAudit.setStatut("INCOMPLET");
            sendMissingDocumentsNotification(dossierAudit);
        }
    
        return dossierAuditRepository.save(dossierAudit);
    }
    

//Génération PDF et enregistrement du lien
public String generatePdfAndGetUrl(DossierAudit dossierAudit) throws IOException {
    String fileName = "dossier_audit_" + dossierAudit.getIdDossierAudit() + ".pdf";
    String filePath = "/chemin/vers/pdf/" + fileName;

    FileOutputStream fos = new FileOutputStream(filePath);
    com.lowagie.text.Document pdfDoc = new com.lowagie.text.Document(PageSize.A4);
    PdfWriter.getInstance(pdfDoc, fos);

    pdfDoc.open();
    // ... ajouter les Paragraph comme avant ...
    pdfDoc.close();
    fos.close();

    return "/api/fichiers/" + fileName; // ou une URL publique
}

//
    public void generateAuditReport(Long dossierAuditId, HttpServletResponse response) throws IOException {
        DossierAudit dossierAudit = dossierAuditRepository.findById(dossierAuditId)
                .orElseThrow(() -> new RuntimeException("❌ Dossier introuvable avec l'ID : " + dossierAuditId));
    
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=dossier_audit_" + dossierAuditId + ".pdf");
    
        com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, new BufferedOutputStream(response.getOutputStream()));    
        document.open();
    
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph title = new Paragraph("Dossier Audit - " + dossierAudit.getIdDossierAudit(), fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
    
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);
    
        document.add(new Paragraph("\nStatut: " + dossierAudit.getStatut(), fontParagraph));
        document.add(new Paragraph("Date de création: " + dossierAudit.getCreationDate(), fontParagraph));
    
        for (Document doc : dossierAudit.getDocuments()) {
            document.add(new Paragraph("\n📜 Document: " + doc.getTitle(), fontParagraph));
            document.add(new Paragraph("Description: " + doc.getDescription(), fontParagraph));
            document.add(new Paragraph("Type: " + doc.getType(), fontParagraph));
        }
    
        document.close();
    }


//Methode vérification completude doc 
public boolean areAllRequiredDocumentsPresent(List<Document> documents) {
    // 🧠 Exemple : tu définis une liste de types de documents obligatoires
    List<String> requiredTypes = List.of("Rapport", "Certificat", "Justificatif");

    for (String type : requiredTypes) {
        boolean found = documents.stream().anyMatch(d -> d.getType().equalsIgnoreCase(type));
        if (!found) return false;
    }
    return true;
}

private void sendMissingDocumentsNotification(DossierAudit dossierAudit) {
    // TODO: à implémenter plus tard avec envoi de notification (email, SMS, etc.)
    System.out.println("📣 Dossier incomplet - Notification fictive (à implémenter)");
}


}