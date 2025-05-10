package com.certifdoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.certifdoc.entity.LogEntity;

public interface LogRepository extends JpaRepository<LogEntity, Long> {
    // Méthodes personnalisées si nécessaire
    
}
