package com.certifdoc.dto;

import java.util.List;

import lombok.Data;
@Data
public class DocumentIdListDTO {
    private List<Long> documentIds;

    public List<Long> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(List<Long> documentIds) {
        this.documentIds = documentIds;
    }

}
