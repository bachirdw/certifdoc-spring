package com.certifdoc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "historique_modification")
public class HistoriqueModificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistory;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modification_date", nullable = false)
    private Date modificationDate;

    @Column(name = "change_description", nullable = false)
    private String changeDescription;
    
    // Relation : chaque historique est lié à un document
    @ManyToOne
    @JoinColumn(name = "idDocument")
    private DocumentEntity document;

    
}
