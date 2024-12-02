package com.professional.model.services;

import com.professional.model.entities.VerificationToken;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementación del servicio VerificationTokenService.
 */
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public VerificationToken createVerificationToken(String correo) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiration = LocalDateTime.now().plusHours(24); // Token válido por 24 horas
        VerificationToken verificationToken = new VerificationToken(token, correo, expiration);
        return verificationTokenRepository.save(verificationToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token de verificación no encontrado: " + token));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteVerificationToken(String token) {
        VerificationToken verificationToken = getVerificationToken(token);
        verificationTokenRepository.delete(verificationToken);
    }
}

