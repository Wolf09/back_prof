package com.professional.controller;

import com.professional.model.dto.RegistroDTO;
import com.professional.model.dto.LoginDTO;
import com.professional.model.dto.Error;
import com.professional.model.services.AuthService;
import jakarta.validation.Validator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final Validator validator;

    @Autowired
    public AuthController(AuthService authService, Validator validator) {
        this.authService = authService;
        this.validator = validator;
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
            Map<String, String> response = new HashMap<>();
            response.put("data","Registro exitoso. Por favor, verifica tu correo para confirmar tu cuenta.");
            return new ResponseEntity<>(response, HttpStatus.OK);
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
            Map<String, String> response = new HashMap<>();
            response.put("data","Cuenta confirmada exitosamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
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
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}

