package com.certifdoc.controller;

import com.certifdoc.entity.DocumentKeyword;
import com.certifdoc.service.DocumentKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/document-keywords")
public class DocumentKeywordController {

    @Autowired
    private DocumentKeywordService documentKeywordService;

    // Récupérer tous les mots-clés
    @GetMapping
    public List<DocumentKeyword> getAllKeywords() {
        return documentKeywordService.getAllKeywords();
    }

    // Récupérer un mot-clé par ID
    @GetMapping("/{id}")
    public DocumentKeyword getKeywordById(@PathVariable Long id) {
        return documentKeywordService.getKeywordById(id);
    }

    // Ajouter un mot-clé
    @PostMapping
    public DocumentKeyword addKeyword(@RequestBody DocumentKeyword documentKeyword) {
        return documentKeywordService.addKeyword(documentKeyword);
    }
}