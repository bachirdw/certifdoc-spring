package com.certifdoc.service;

import com.certifdoc.entity.KeywordEntity;
import com.certifdoc.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    // Récupérer tous les mots-clés
    public List<KeywordEntity> getAllKeywords() {
        return keywordRepository.findAll();
    }

    // Récupérer un mot-clé par ID
    public KeywordEntity getKeywordById(Long idKeyword) {
        return keywordRepository.findById(idKeyword)
                .orElseThrow(() -> new RuntimeException("❌ Mot-clé introuvable avec l'ID : " + idKeyword));
    }

    // Ajouter un mot-clé
    public KeywordEntity addKeyword(KeywordEntity keyword) {
        return keywordRepository.save(keyword);
    }
}