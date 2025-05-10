package com.certifdoc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "dossier_audit")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DossierAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDossierAudit;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "statut", nullable = false)
    private String statut; // Actif, Clôturé, En attente...

    @Column(name = "url_pdf")
    private String urlPdf; // URL du fichier PDF du dossier
 
    @OneToMany(mappedBy = "dossierAudit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DocumentEntity> documents;

public void setDocuments(List<DocumentEntity> documents) {
    this.documents = documents;
}

@Lob
@Column(name = "pdf_content", columnDefinition = "LONGBLOB")
private byte[] pdfContent;

    
}