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
    private Date modificationDate;

    private String changeDescription;

    @ManyToOne
    @JoinColumn(name = "idDocument")
    private DocumentEntity document;

    
}
