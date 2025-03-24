package com.professional.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class FiltroTrabajoIndependienteDTO {
    @Id
    private Long idIndependiente;

    @Id
    private Long idTrabajoInd;

    private String nombres;

    private String apellidos;

    @Column(name = "foto_representante")
    private String fotoRepresentante;

    @Column(name = "tipo_usuario")
    private String tipoUsuario;

    private String profesion;

    @Column(name = "area_trabajo")
    private String areaTrabajo;

    private String pais;

    private String ciudad;

    @Column(name = "descripcion_corta")
    private String descripcionCorta;

    private String descripcion;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "ventas")
    private Long ventas;

    private String celular;

    public FiltroTrabajoIndependienteDTO(Long idIndependiente, Long idTrabajoInd, String nombres, String apellidos, String fotoRepresentante, String tipoUsuario, String profesion, String areaTrabajo, String pais, String ciudad, String descripcionCorta, String descripcion, Double averageRating, Double precio, Long ventas, String celular) {
        this.idIndependiente = idIndependiente;
        this.idTrabajoInd = idTrabajoInd;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fotoRepresentante = fotoRepresentante;
        this.tipoUsuario = tipoUsuario;
        this.profesion = profesion;
        this.areaTrabajo = areaTrabajo;
        this.pais = pais;
        this.ciudad = ciudad;
        this.descripcionCorta = descripcionCorta;
        this.descripcion = descripcion;
        this.averageRating = averageRating;
        this.precio = precio;
        this.ventas = ventas;
        this.celular = celular;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
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

    public Long getVentas() {
        return ventas;
    }

    public void setVentas(Long ventas) {
        this.ventas = ventas;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
