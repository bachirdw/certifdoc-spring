package com.certifdoc.exception;

// Importation des classes nécessaires pour la gestion des exceptions et des réponses HTTP
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

import jakarta.persistence.NoResultException;

@RestControllerAdvice // Indique que cette classe va gérer les exceptions globalement pour les contrôleurs REST
public class ExceptionHandling {

  
}
