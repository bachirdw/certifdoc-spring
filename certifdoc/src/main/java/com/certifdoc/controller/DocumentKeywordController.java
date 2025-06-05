package com.certifdoc.controller;

import com.certifdoc.entity.DocumentKeywordEntity;
import com.certifdoc.service.DocumentKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/document-keywords")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentKeywordController {

    @Autowired
    private DocumentKeywordService documentKeywordService;

    // Récupérer tous les mots-clés
    @GetMapping
    public List<DocumentKeywordEntity> getAllKeywords() {
        return documentKeywordService.getAllKeywords();
    }

    // Récupérer un mot-clé par ID
    @GetMapping("/{id}")
    public DocumentKeywordEntity getKeywordById(@PathVariable Long id) {
        return documentKeywordService.getKeywordById(id);
    }

    // Ajouter un mot-clé
    @PostMapping
    public DocumentKeywordEntity addKeyword(@RequestBody DocumentKeywordEntity documentKeyword) {
        return documentKeywordService.addKeyword(documentKeyword);
    }
}