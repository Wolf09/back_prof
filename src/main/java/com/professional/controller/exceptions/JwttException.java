package com.professional.controller.exceptions;

public class JwttException extends RuntimeException{
    public JwttException(String message) {
        super(message);
    }
}
