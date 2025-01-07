package com.professional.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Profesional {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 60, message = "El nombre debe tener entre 2 y 60 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 60, message = "Los apellidos deben tener entre 2 y 60 caracteres")
    private String apellidos;

    @Column(name = "foto_representante")
    private String fotoRepresentante;
    @NotBlank(message = "El celular es obligatorio")
    @Pattern(regexp = "^(1|[5-9][0-9]{2})\\d{7,10}$", message = "El celular debe tener un formato válido, por ejemplo: 59170735671")
    private String celular;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 60, message = "La contraseña debe tener entre 8 y 60 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,60}$",
            message = "La contraseña debe incluir al menos una letra mayúscula, un número y un carácter especial")
    @Column(length = 200) // Para acomodar el hash de BCrypt
    private String password;
    @NotBlank
    @Column(name = "carta_presentacion")
    private String cartaPresentacion;

    private String mision;

    private String vision;

    @Column(name = "dni_anverso")
    private String dniAnverso;

    @Column(name = "dni_reverso")
    private String dniReverso;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @Column(name = "fecha_apertura")
    private LocalDateTime fechaApertura;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @Column(name = "fecha_pago_inicio")
    private LocalDateTime fechaPagoInicio;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @Column(name = "fecha_pago_fin")
    private LocalDateTime fechaPagoFin;
    @NotNull
    @Column(name = "activo", nullable = false)
    protected Boolean activo;

    @NotNull
    @Column(name = "tipo_usuario", nullable = false)
    private String tipoUsuario;


    @PrePersist
    protected void onCreate() {
        this.activo = true;
        this.fechaApertura = LocalDateTime.now();
        this.fechaPagoInicio= LocalDateTime.now();
        this.fechaPagoFin= LocalDateTime.now().plusMonths(3);
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFotoRepresentante() {
        return fotoRepresentante;
    }

    public void setFotoRepresentante(String fotoRepresentante) {
        this.fotoRepresentante = fotoRepresentante;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCartaPresentacion() {
        return cartaPresentacion;
    }

    public void setCartaPresentacion(String cartaPresentacion) {
        this.cartaPresentacion = cartaPresentacion;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getDniAnverso() {
        return dniAnverso;
    }

    public void setDniAnverso(String dniAnverso) {
        this.dniAnverso = dniAnverso;
    }

    public String getDniReverso() {
        return dniReverso;
    }

    public void setDniReverso(String dniReverso) {
        this.dniReverso = dniReverso;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDateTime fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public LocalDateTime getFechaPagoInicio() {
        return fechaPagoInicio;
    }

    public void setFechaPagoInicio(LocalDateTime fechaPagoInicio) {
        this.fechaPagoInicio = fechaPagoInicio;
    }

    public LocalDateTime getFechaPagoFin() {
        return fechaPagoFin;
    }

    public void setFechaPagoFin(LocalDateTime fechaPagoFin) {
        this.fechaPagoFin = fechaPagoFin;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
