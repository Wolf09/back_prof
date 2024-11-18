package com.professional.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @Column(name = "fecha_cambio", nullable = false, updatable = false)
    private LocalDateTime fechaCambio;

    // Relación Muchos a Uno con Cliente (creador del TrabajoIndEnAccion)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    @JsonIgnore
    private Cliente cliente;

    // Campo para almacenar el estado anterior
    @Transient
    private EstadoTrabajo estadoTrabajoAnterior;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @PrePersist
    protected void onCreate() {
        if (this.estadoTrabajo == null) {
            this.estadoTrabajo = EstadoTrabajo.PENDIENTE;
        }
        this.fechaCambio = LocalDateTime.now();
        this.activo = true; // Establecer activo en true al crear
    }

    // Constructor por defecto
    public TrabajoIndEnAccion() {
        this.estadoTrabajoAnterior = estadoTrabajo;
        this.activo = true;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
