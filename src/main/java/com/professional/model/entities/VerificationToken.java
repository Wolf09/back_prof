package com.professional.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa un token de verificación para confirmar cuentas de usuarios.
 */
@Entity
@Table(name = "verification_tokens")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private LocalDateTime fechaExpiracion;

    // Constructor por defecto
    public VerificationToken() {
    }

    // Constructor con parámetros
    public VerificationToken(String token, String correo, LocalDateTime fechaExpiracion) {
        this.token = token;
        this.correo = correo;
        this.fechaExpiracion = fechaExpiracion;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    // No se incluye setId ya que es autogenerado

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
}

