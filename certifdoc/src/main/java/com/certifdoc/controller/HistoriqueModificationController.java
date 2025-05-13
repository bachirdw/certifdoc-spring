package com.certifdoc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certifdoc.dto.HistoriqueModificationDTO;
import com.certifdoc.entity.HistoriqueModificationEntity;
import com.certifdoc.exception.ResourceNotFoundException;
import com.certifdoc.service.HistoriqueModificationService;

@RestController
@RequestMapping("/api/historiques")
public class HistoriqueModificationController {

    @Autowired
    private HistoriqueModificationService historiqueService;

    // 🔍 Obtenir tous les historiques
    @GetMapping
    public List<HistoriqueModificationEntity> getAllHistoriques() {
        return historiqueService.getAllHistoriques();
    }

    // 🔍 Obtenir un historique par son ID
    @GetMapping("/{idHistory}")
    public ResponseEntity<HistoriqueModificationEntity> getHistoriqueById(@PathVariable Long id) {
        try {
            HistoriqueModificationEntity historique = historiqueService.getHistoriqueById(id);
            return ResponseEntity.ok(historique);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ➕ Ajouter un nouvel historique
    @PostMapping
    public HistoriqueModificationEntity addHistorique(@RequestBody HistoriqueModificationDTO dto) {
        return historiqueService.addHistorique(dto);
    }

    // 🔁 Modifier un historique existant
    @PutMapping("/{idHistory}")
    public ResponseEntity<HistoriqueModificationEntity> updateHistorique(
            @PathVariable Long id,
            @RequestBody HistoriqueModificationDTO dto) {
        try {
            HistoriqueModificationEntity updated = historiqueService.updateHistorique(id, dto);
            return ResponseEntity.ok(updated);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ❌ Supprimer un historique
    @DeleteMapping("/{idHistory}")
    public ResponseEntity<Void> deleteHistorique(@PathVariable Long id) {
        try {
            historiqueService.deleteHistorique(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
