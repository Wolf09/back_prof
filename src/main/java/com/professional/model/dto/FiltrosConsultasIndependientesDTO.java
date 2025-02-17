package com.professional.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FiltrosConsultasIndependientesDTO {

    @Id
    private Long idIndependiente;

    @Id
    private Long idTrabajoInd;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 60, message = "El nombre debe tener entre 2 y 60 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 60, message = "Los apellidos deben tener entre 2 y 60 caracteres")
    private String apellidos;

    @Column(name = "foto_representante")
    private String fotoRepresentante;

    @NotNull
    @Column(name = "tipo_usuario", nullable = false)
    private String tipoUsuario;

    @NotBlank
    private String profesion;

    @Column(name = "area_trabajo")
    @NotBlank
    private String areaTrabajo;

    @NotBlank(message = "La descripción del trabajo es obligatoria")
    private String descripcion;

    // almacena el promedio de las calificaciones recibidas para este trabajo
    @Column(name = "average_rating", nullable = false)
    private Double averageRating;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "ventas", nullable = false)
    private Long ventas;

    public FiltrosConsultasIndependientesDTO(Long idIndependiente, Long idTrabajoInd, String nombres, String apellidos, String fotoRepresentante, String tipoUsuario, String profesion, String areaTrabajo, String descripcion, Double averageRating, Double precio) {
        this.idIndependiente = idIndependiente;
        this.idTrabajoInd = idTrabajoInd;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fotoRepresentante = fotoRepresentante;
        this.tipoUsuario = tipoUsuario;
        this.profesion = profesion;
        this.areaTrabajo = areaTrabajo;
        this.descripcion = descripcion;
        this.averageRating = averageRating;
        this.precio = precio;
    }

    public FiltrosConsultasIndependientesDTO(Long idIndependiente, Long idTrabajoInd, String nombres, String apellidos, String fotoRepresentante, String tipoUsuario, String profesion, String areaTrabajo, String descripcion, Double averageRating, Double precio, Long ventas) {
        this.idIndependiente = idIndependiente;
        this.idTrabajoInd = idTrabajoInd;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fotoRepresentante = fotoRepresentante;
        this.tipoUsuario = tipoUsuario;
        this.profesion = profesion;
        this.areaTrabajo = areaTrabajo;
        this.descripcion = descripcion;
        this.averageRating = averageRating;
        this.precio = precio;
        this.ventas = ventas;
    }

    public FiltrosConsultasIndependientesDTO() {
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

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getAreaTrabajo() {
        return areaTrabajo;
    }

    public void setAreaTrabajo(String areaTrabajo) {
        this.areaTrabajo = areaTrabajo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getIdIndependiente() {
        return idIndependiente;
    }

    public void setIdIndependiente(Long idIndependiente) {
        this.idIndependiente = idIndependiente;
    }

    public Long getIdTrabajoInd() {
        return idTrabajoInd;
    }

    public void setIdTrabajoInd(Long idTrabajoInd) {
        this.idTrabajoInd = idTrabajoInd;
    }
}
