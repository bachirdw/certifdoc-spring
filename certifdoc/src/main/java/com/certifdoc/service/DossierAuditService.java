package com.certifdoc.service;

import com.certifdoc.entity.DocumentEntity;
import com.certifdoc.entity.DossierAuditEntity;
import com.certifdoc.exception.DossierAuditNotFoundException;
import com.certifdoc.repository.DocumentRepository;
import com.certifdoc.repository.DossierAuditRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DossierAuditService {

    @Autowired
    private DossierAuditRepository dossierAuditRepository;

    @Autowired
    private DocumentRepository documentRepository;

    public List<DossierAuditEntity> getAllDossiers() {
        return dossierAuditRepository.findAll();
    }
// Récupérer un dossier par ID
    public DossierAuditEntity getDossierById(Long idDossierAudit) {
        return dossierAuditRepository.findById(idDossierAudit)
                .orElseThrow(() -> new DossierAuditNotFoundException("❌ Dossier introuvable avec l'ID : " + idDossierAudit));
    }

    public DossierAuditEntity updateDossier(Long idDossierAudit, DossierAuditEntity updatedDossier) {
        DossierAuditEntity existingDossier = getDossierById(idDossierAudit);
        existingDossier.setStatut(updatedDossier.getStatut());
        existingDossier.setUrlPdf(updatedDossier.getUrlPdf());
        return dossierAuditRepository.save(existingDossier);
    }

    public void deleteDossierById(Long id) {
        DossierAuditEntity dossierAudit = getDossierById(id);
        dossierAuditRepository.delete(dossierAudit);
    }

 
    /**
     * ✅ UC_VerifyCompleteness - Vérification complétude des documents
     */

    public boolean verifyDocumentCompleteness(List<Long> documentIds) {
        List<DocumentEntity> documents = documentRepository.findAllById(documentIds);
        //  méthode vérifie **si les types des documents sélectionnés contiennent les 3 types EXACTS, sinon elle retourne false.
       // faudra rendre plus flexible pour accepter d'autres types de documents
        List<String> requiredTypes = List.of("Rapport", "Certificat", "Justificatif");

        Set<String> typesPresents = documents.stream()
                .map(DocumentEntity::getType)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return requiredTypes.stream().allMatch(type -> typesPresents.contains(type.toLowerCase()));
    }

    /**
     * ✅ Méthode principale pour créer un dossier d'audit,
     * associer des documents et générer le PDF directement en BDD (en byte[]).
     */
    public DossierAuditEntity generateDossierAuditWithPdfInDb(DossierAuditEntity dossierAudit, List<Long> documentIds) {
         // Étape 4 : Ajout des documents
        List<DocumentEntity> documentEntities = documentRepository.findAllById(documentIds);
        dossierAudit.setDocuments(documentEntities);
        dossierAudit.setCreationDate(new Date());
        dossierAudit.setStatut("COMPLET");

        dossierAudit = dossierAuditRepository.save(dossierAudit);

        try {
            byte[] pdfContent = generatePdfContent(dossierAudit);
            dossierAudit.setPdfContent(pdfContent);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la génération du PDF pour le dossier d'audit", e);
        }

        return dossierAuditRepository.save(dossierAudit);
    }
 /**
     * Génère un PDF structuré selon le format Qualiopi
     */
    private byte[] generatePdfContent(DossierAuditEntity dossierAudit) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
// Structure du document
        document.open();
        document.add(new Paragraph("📘 Dossier d'Audit Qualiopi"));
        document.add(new Paragraph("ID Dossier: " + dossierAudit.getIdDossierAudit()));
        document.add(new Paragraph("Date: " + dossierAudit.getCreationDate()));
        document.add(new Paragraph(" "));

        for (DocumentEntity doc : dossierAudit.getDocuments()) {
            document.add(new Paragraph("• " + doc.getType()));
        }

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    private void sendMissingDocumentsNotification(DossierAuditEntity dossierAudit) {
        System.out.println("📣 Dossier incomplet - Notification fictive (à implémenter)");
    }
}
