package com.professional.model.services;

public interface EmailService {
    void enviarEmail(String to, String subject, String body);
}

