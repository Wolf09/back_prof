package com.professional.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "trabajos_ind_en_accion")
public class TrabajoIndEnAccion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Muchos a Uno con TrabajoIndependiente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trabajo_independiente_id", nullable = false)
    @NotNull(message = "El trabajo independiente es obligatorio")
    @JsonIgnore
    private TrabajoIndependiente trabajoIndependiente;

    @NotNull(message = "El estado del trabajo es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_trabajo", nullable = false)
    private EstadoTrabajo estadoTrabajo;

    @Column(name = "fecha_cambio", nullable = false, updatable = false)
    private LocalDateTime fechaCambio;

    // Campo para almacenar el estado anterior
    @Transient
    private EstadoTrabajo estadoTrabajoAnterior;

    @PrePersist
    protected void onCreate() {
        if (this.estadoTrabajo == null) {
            this.estadoTrabajo = EstadoTrabajo.PENDIENTE;
        }
        this.fechaCambio = LocalDateTime.now();
    }

    // Constructor por defecto
    public TrabajoIndEnAccion() {
        this.estadoTrabajoAnterior = estadoTrabajo;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public TrabajoIndependiente getTrabajoIndependiente() {
        return trabajoIndependiente;
    }

    public void setTrabajoIndependiente(TrabajoIndependiente trabajoIndependiente) {
        this.trabajoIndependiente = trabajoIndependiente;
    }

    public EstadoTrabajo getEstadoTrabajo() {
        return estadoTrabajo;
    }

    public void setEstadoTrabajo(EstadoTrabajo estadoTrabajo) {
        this.estadoTrabajo = estadoTrabajo;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    // No setter para fechaCambio ya que se establece automáticamente

    // toString (opcional)


    public void setId(Long id) {
        this.id = id;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    // Método para inicializar el estado anterior después de cargar la entidad
    @PostLoad
    public void init() {
        this.estadoTrabajoAnterior = this.estadoTrabajo;
    }

    // Listener para actualizar fechaCambio antes de actualizar la entidad
    @PreUpdate
    public void preUpdate() {
        if (this.estadoTrabajo != null && !this.estadoTrabajo.equals(this.estadoTrabajoAnterior)) {
            this.fechaCambio = LocalDateTime.now();
            this.estadoTrabajoAnterior = this.estadoTrabajo;
        }
    }

    // Serial UID
    @Serial
    private static final long serialVersionUID = 1L;
}
