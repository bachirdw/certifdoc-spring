package com.certifdoc.service;

import com.certifdoc.entity.DocumentKeywordEntity;
import com.certifdoc.repository.DocumentKeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentKeywordService {

    @Autowired
    private DocumentKeywordRepository documentKeywordRepository;

    // Récupérer tous les mots-clés
    public List<DocumentKeywordEntity> getAllKeywords() {
        return documentKeywordRepository.findAll();
    }

    // Récupérer un mot-clé par ID
    public DocumentKeywordEntity getKeywordById(Long idDocumentKeyword) {
        return documentKeywordRepository.findById(idDocumentKeyword)
                .orElseThrow(() -> new RuntimeException("❌ Mot-clé introuvable avec l'ID : " + idDocumentKeyword));
    }

    // Ajouter un mot-clé
    public DocumentKeywordEntity addKeyword(DocumentKeywordEntity documentKeyword) {
        return documentKeywordRepository.save(documentKeyword);
    }
}