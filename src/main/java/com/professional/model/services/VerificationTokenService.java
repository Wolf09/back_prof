package com.professional.model.services;

import com.professional.model.entities.VerificationToken;

/**
 * Servicio para manejar operaciones relacionadas con VerificationToken.
 */
public interface VerificationTokenService {

    /**
     * Crea un nuevo token de verificación para un correo electrónico específico.
     *
     * @param correo El correo electrónico del usuario.
     * @return El VerificationToken creado.
     */
    VerificationToken createVerificationToken(String correo);

    /**
     * Recupera un VerificationToken por su token.
     *
     * @param token El valor del token.
     * @return El VerificationToken encontrado.
     * @throws com.professional.model.exceptions.ResourceNotFoundException Si el token no se encuentra.
     */
    VerificationToken getVerificationToken(String token);

    /**
     * Elimina un VerificationToken por su token.
     *
     * @param token El valor del token a eliminar.
     * @throws com.professional.model.exceptions.ResourceNotFoundException Si el token no se encuentra.
     */
    void deleteVerificationToken(String token);
}

