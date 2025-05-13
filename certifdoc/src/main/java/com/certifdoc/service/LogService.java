package com.certifdoc.service;

import com.certifdoc.entity.LogEntity;
import com.certifdoc.entity.UserEntity;
import com.certifdoc.repository.LogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogService {

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

    // pour recuperer les logs d'un utilisateur
public LogEntity enregistrerAction(String action, UserEntity utilisateur) {
        LogEntity log = new LogEntity();
        log.setAction(action);
        log.setTimestamp(new Date());
        log.setUtilisateur(utilisateur);
        return logRepository.save(log);
    }


}