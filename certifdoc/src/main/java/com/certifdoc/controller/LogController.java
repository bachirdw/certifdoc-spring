package com.certifdoc.controller;

import com.certifdoc.entity.LogEntity;
import com.certifdoc.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    // Récupérer tous les logs
    @GetMapping
    public List<LogEntity> getAllLogs() {
        return logService.getAllLogs();
    }

    // Récupérer un log par ID
    @GetMapping("/{id}")
    public LogEntity getLogById(@PathVariable Long id) {
        return logService.getLogById(id);
    }

    // Ajouter un log
    @PostMapping
    public LogEntity addLog(@RequestBody LogEntity log) {
        return logService.addLog(log);
    }
}