package com.professional.model.dto;

import com.professional.model.enums.EstadoTrabajo;
import jakarta.persistence.*;

public class TrabajoEmpresaEnAccionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpresa;

    @Column(name = "descripcion_corta")
    private String descripcionCorta;
    private String descripcion;

    private Double precio;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpresaEnAccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_trabajo")
    private EstadoTrabajo estadoTrabajo;

    public TrabajoEmpresaEnAccionDTO(Long idEmpresa, String descripcionCorta, String descripcion, Double precio, Long idEmpresaEnAccion, EstadoTrabajo estadoTrabajo) {
        this.idEmpresa = idEmpresa;
        this.descripcionCorta = descripcionCorta;
        this.descripcion = descripcion;
        this.precio = precio;
        this.idEmpresaEnAccion = idEmpresaEnAccion;
        this.estadoTrabajo = estadoTrabajo;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getIdEmpresaEnAccion() {
        return idEmpresaEnAccion;
    }

    public void setIdEmpresaEnAccion(Long idEmpresaEnAccion) {
        this.idEmpresaEnAccion = idEmpresaEnAccion;
    }

    public EstadoTrabajo getEstadoTrabajo() {
        return estadoTrabajo;
    }

    public void setEstadoTrabajo(EstadoTrabajo estadoTrabajo) {
        this.estadoTrabajo = estadoTrabajo;
    }
}
