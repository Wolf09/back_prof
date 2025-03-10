package com.professional.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FiltrosConsultasEmpresasDTO {

    @Id
    private Long idEmpresa;

    @Id
    private Long idTrabajoEmp;
    @NotBlank
    @Column(name = "nombre_empresa")
    private String nombreEmpresa;

    @Column(name = "foto_representante")
    private String fotoRepresentante;

    @NotNull
    @Column(name = "tipo_usuario", nullable = false)
    private String tipoUsuario;
    @Column(name = "area_trabajo")
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

    public FiltrosConsultasEmpresasDTO(Long idEmpresa, Long idTrabajoEmp, String nombreEmpresa, String fotoRepresentante, String tipoUsuario, String areaTrabajo, String descripcion, Double averageRating, Double precio) {
        this.idEmpresa = idEmpresa;
        this.idTrabajoEmp = idTrabajoEmp;
        this.nombreEmpresa = nombreEmpresa;
        this.fotoRepresentante = fotoRepresentante;
        this.tipoUsuario = tipoUsuario;
        this.areaTrabajo = areaTrabajo;
        this.descripcion = descripcion;
        this.averageRating = averageRating;
        this.precio = precio;
    }

    public FiltrosConsultasEmpresasDTO(Long idEmpresa, Long idTrabajoEmp, String nombreEmpresa, String fotoRepresentante, String tipoUsuario, String areaTrabajo, String descripcion, Double averageRating, Double precio, Long ventas) {
        this.idEmpresa = idEmpresa;
        this.idTrabajoEmp = idTrabajoEmp;
        this.nombreEmpresa = nombreEmpresa;
        this.fotoRepresentante = fotoRepresentante;
        this.tipoUsuario = tipoUsuario;
        this.areaTrabajo = areaTrabajo;
        this.descripcion = descripcion;
        this.averageRating = averageRating;
        this.precio = precio;
        this.ventas = ventas;
    }

    public FiltrosConsultasEmpresasDTO() {
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

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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

    public Long getVentas() {
        return ventas;
    }

    public void setVentas(Long ventas) {
        this.ventas = ventas;
    }
}
