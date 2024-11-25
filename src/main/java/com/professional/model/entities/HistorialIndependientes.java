package com.professional.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_solicitudes")
public class HistorialIndependientes implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Muchos a Uno con Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    @JsonIgnore
    private Cliente cliente;

    // Relación Muchos a Uno con TrabajoIndependiente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trabajo_id", nullable = false)
    @NotNull(message = "El trabajo es obligatorio")
    @JsonIgnore
    private TrabajoIndependiente trabajo;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @Column(name = "fecha_solicitud", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "comentarios")
    private String comentarios;

    @NotNull
    private Boolean activo;

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    // Constructor por defecto
    public HistorialIndependientes() {
        // Inicializar activo como true por defecto
        this.activo = true;
    }

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.activo == null) {
            this.activo = true;
        }
    }

// Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaCreacion;
    }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaCreacion = fechaSolicitud;
    }
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TrabajoIndependiente getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(TrabajoIndependiente trabajo) {
        this.trabajo = trabajo;
    }


    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Serial
    private static final long serialVersionUID = 1L;

}
