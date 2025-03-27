package com.certifdoc.exception;

// Exception personnalisée pour signaler qu'un document n'a pas été trouvé
public class DocumentNotFoundException extends RuntimeException {
    
    public DocumentNotFoundException(String message) {
        super(message);
    }
}