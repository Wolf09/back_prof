package com.professional.controller.exceptions;

/**
 * Excepción personalizada que se lanza cuando se intenta crear un recurso que ya existe.
 */
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
