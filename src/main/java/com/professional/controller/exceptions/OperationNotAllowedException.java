package com.professional.controller.exceptions;

/**
 * Excepción personalizada que se lanza cuando una operación no está permitida.
 */
public class OperationNotAllowedException extends RuntimeException {
    public OperationNotAllowedException(String message) {
        super(message);
    }

    public OperationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
