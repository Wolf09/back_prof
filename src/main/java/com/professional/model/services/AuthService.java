package com.professional.model.services;

import com.professional.model.dto.RegistroDTO;
import com.professional.model.dto.LoginDTO;

public interface AuthService {

    /**
     * Registra un nuevo usuario según el tipo especificado.
     *
     * @param registroDTO Datos del usuario a registrar.
     */
    void registrarUsuario(RegistroDTO registroDTO);

    /**
     * Confirma una cuenta utilizando un token de confirmación.
     *
     * @param token Token de confirmación.
     */
    void confirmarCuenta(String token);

    /**
     * Autentica a un usuario y retorna un token de autenticación.
     *
     * @param loginDTO Datos de autenticación.
     * @return Token de autenticación.
     */
    String autenticarUsuario(LoginDTO loginDTO);
}

