package com.certifdoc.exception;

/**
 * Exception personnalisée pour les ressources non trouvées
 📌 Rôle :
 Sert à signaler qu'une entité ou ressource n'existe pas en base de données.

 Elle est utilisée notamment dans les méthodes des Repository ou Service pour
 améliorer la gestion des erreurs.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
