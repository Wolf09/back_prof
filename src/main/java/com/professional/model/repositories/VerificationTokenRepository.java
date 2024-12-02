package com.professional.model.repositories;

import com.professional.model.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad VerificationToken.
 */
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    /**
     * Encuentra un token de verificación por su valor de token.
     *
     * @param token El valor del token.
     * @return Un Optional que contiene el VerificationToken si se encuentra, o vacío si no.
     */
    Optional<VerificationToken> findByToken(String token);
}

