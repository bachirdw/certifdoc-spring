package com.certifdoc.controller;

import com.certifdoc.entity.KeywordEntity;
import com.certifdoc.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keywords")
public class KeywordController {

    @Autowired
    private KeywordService keywordService;

    // Récupérer tous les mots-clés
    @GetMapping
    public List<KeywordEntity> getAllKeywords() {
        return keywordService.getAllKeywords();
    }

    // Récupérer un mot-clé par ID
    @GetMapping("/{idDocumentKeyword}")
    public KeywordEntity getKeywordById(@PathVariable Long id) {
        return keywordService.getKeywordById(id);
    }

    // Ajouter un mot-clé
    @PostMapping
    public KeywordEntity addKeyword(@RequestBody KeywordEntity keyword) {
        return keywordService.addKeyword(keyword);
    }
}