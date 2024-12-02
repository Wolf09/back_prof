package com.professional.controller;

import com.professional.model.dto.RegistroDTO;
import com.professional.model.dto.LoginDTO;
import com.professional.model.dto.Error;
import com.professional.model.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registro de nuevos usuarios.
     *
     * @param registroDTO Datos del usuario a registrar.
     * @param result      Resultado de la validación.
     * @return Mensaje de éxito o errores.
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody RegistroDTO registroDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        try {
            authService.registrarUsuario(registroDTO);
            return new ResponseEntity<>("Registro exitoso. Por favor, verifica tu correo para confirmar tu cuenta.", HttpStatus.OK);
        } catch (Exception ex) {
            List<Error> errores = new ArrayList<>();
            errores.add(new Error(ex.getMessage()));
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Confirmación de cuenta mediante token enviado por correo.
     *
     * @param token Token de confirmación.
     * @return Mensaje de éxito o errores.
     */
    @GetMapping("/confirmar-cuenta")
    public ResponseEntity<?> confirmarCuenta(@RequestParam("token") String token) {
        try {
            authService.confirmarCuenta(token);
            return new ResponseEntity<>("Cuenta confirmada exitosamente.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Inicio de sesión de usuarios.
     *
     * @param loginDTO Datos de autenticación.
     * @param result   Resultado de la validación.
     * @return Token de autenticación o errores.
     */
    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        try {
            String token = authService.autenticarUsuario(loginDTO);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}

