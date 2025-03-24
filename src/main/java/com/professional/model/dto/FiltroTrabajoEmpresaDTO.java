package com.professional.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class FiltroTrabajoEmpresaDTO {

    @Id
    private Long idEmpresa;

    @Id
    private Long idTrabajoEmp;

    @Column(name = "nombre_empresa")
    private String nombreEmpresa;

    @Column(name = "foto_representante")
    private String fotoRepresentante;


    @Column(name = "tipo_usuario", nullable = false)
    private String tipoUsuario;
    @Column(name = "area_trabajo")
    private String areaTrabajo;


    @Column(name = "descripcion_corta")
    private String descripcionCorta;

    private String descripcion;

    // almacena el promedio de las calificaciones recibidas para este trabajo
    @Column(name = "average_rating", nullable = false)
    private Double averageRating;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "ventas", nullable = false)
    private Long ventas;

    private String pais;

    private String ciudad;

    private String celular;

    public FiltroTrabajoEmpresaDTO(Long idEmpresa, Long idTrabajoEmp, String nombreEmpresa, String fotoRepresentante, String tipoUsuario, String areaTrabajo, String descripcionCorta, String descripcion, Double averageRating, Double precio, Long ventas, String pais, String ciudad, String celular) {
        this.idEmpresa = idEmpresa;
        this.idTrabajoEmp = idTrabajoEmp;
        this.nombreEmpresa = nombreEmpresa;
        this.fotoRepresentante = fotoRepresentante;
        this.tipoUsuario = tipoUsuario;
        this.areaTrabajo = areaTrabajo;
        this.descripcionCorta = descripcionCorta;
        this.descripcion = descripcion;
        this.averageRating = averageRating;
        this.precio = precio;
        this.ventas = ventas;
        this.pais = pais;
        this.ciudad = ciudad;
        this.celular = celular;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getIdTrabajoEmp() {
        return idTrabajoEmp;
    }

    public void setIdTrabajoEmp(Long idTrabajoEmp) {
        this.idTrabajoEmp = idTrabajoEmp;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
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

    public String getAreaTrabajo() {
        return areaTrabajo;
    }

    public void setAreaTrabajo(String areaTrabajo) {
        this.areaTrabajo = areaTrabajo;
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

}
