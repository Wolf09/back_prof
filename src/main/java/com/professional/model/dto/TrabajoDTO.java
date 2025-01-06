package com.professional.model.dto;

import java.time.LocalDateTime;

/**
 * DTO para representar trabajos independientes y de empresa de manera unificada.
 */
public class TrabajoDTO {
    private Long id;
    private String descripcion;
    private Double averageRating;
    private Boolean activo;
    private Double precio;
    private LocalDateTime fechaCreacion;
    private String tipoTrabajo; // "Independiente" o "Empresa"

    // Constructor
    public TrabajoDTO(Long id, String descripcion, Double averageRating, Boolean activo, Double precio, LocalDateTime fechaCreacion, String tipoTrabajo) {
        this.id = id;
        this.descripcion = descripcion;
        this.averageRating = averageRating;
        this.activo = activo;
        this.precio = precio;
        this.fechaCreacion = fechaCreacion;
        this.tipoTrabajo = tipoTrabajo;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Boolean getActivo() {
        return activo;
    }

    public Double getPrecio() {
        return precio;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setTipoTrabajo(String tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }


}
