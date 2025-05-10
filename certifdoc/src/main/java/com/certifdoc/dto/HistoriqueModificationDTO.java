package com.certifdoc.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO pour représenter un historique de modification
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoriqueModificationDTO {

    private Long idHistory;
    private Date modificationDate;
    private String changeDescription;

}

