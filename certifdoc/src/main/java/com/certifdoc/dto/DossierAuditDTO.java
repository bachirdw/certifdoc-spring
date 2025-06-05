package com.certifdoc.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
public class DossierAuditDTO {

    private Long idDossierAudit;
    private Date creationDate;
    private String statut;
    private String urlPdf;

    // On évite d'envoyer directement les données binaires du PDF pour limiter la charge réseau
    private List<DocumentDTO> documents;
}