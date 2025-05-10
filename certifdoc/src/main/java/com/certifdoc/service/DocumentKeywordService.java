package com.certifdoc.service;

import com.certifdoc.entity.DocumentKeyword;
import com.certifdoc.repository.DocumentKeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentKeywordService {

    @Autowired
    private DocumentKeywordRepository documentKeywordRepository;

    // Récupérer tous les mots-clés
    public List<DocumentKeyword> getAllKeywords() {
        return documentKeywordRepository.findAll();
    }

    // Récupérer un mot-clé par ID
    public DocumentKeyword getKeywordById(Long idDocumentKeyword) {
        return documentKeywordRepository.findById(idDocumentKeyword)
                .orElseThrow(() -> new RuntimeException("❌ Mot-clé introuvable avec l'ID : " + idDocumentKeyword));
    }

    // Ajouter un mot-clé
    public DocumentKeyword addKeyword(DocumentKeyword documentKeyword) {
        return documentKeywordRepository.save(documentKeyword);
    }
}