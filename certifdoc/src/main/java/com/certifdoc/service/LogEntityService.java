package com.certifdoc.service;

import com.certifdoc.entity.LogEntity;
import com.certifdoc.repository.LogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogEntityService {

    @Autowired
    private LogRepository logRepository;

    // Récupérer tous les logs
    public List<LogEntity> getAllLogs() {
        return logRepository.findAll();
    }

    // Récupérer un log par ID
    public LogEntity getLogById(Long idLog) {
        return logRepository.findById(idLog)
                .orElseThrow(() -> new RuntimeException("❌ Log introuvable avec l'ID : " + idLog));
    }

    // Ajouter un log
    public LogEntity addLog(LogEntity log) {
        return logRepository.save(log);
    }
}