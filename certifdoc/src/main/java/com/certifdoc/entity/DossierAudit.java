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
public class DossierAudit {

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
    private List<Document> documents;

public void setDocuments(List<Document> documents) {
    this.documents = documents;
}
    
}