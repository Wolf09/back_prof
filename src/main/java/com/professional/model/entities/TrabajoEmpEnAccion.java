package com.professional.model.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.professional.model.enums.EstadoTrabajo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "trabajos_emp_en_accion")
public class TrabajoEmpEnAccion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "estado_trabajo")
    private EstadoTrabajo estadoTrabajo;

    @Column(name = "fecha_cambio", updatable = false)
    private LocalDateTime fechaCambio;

    // Relación Muchos a Uno con TrabajoEmpresa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trabajo_empresa_id")
    @JsonIgnore
    private TrabajoEmpresa trabajoEmpresa;

    // Campo para almacenar el estado anterior
    @Transient
    private EstadoTrabajo estadoTrabajoAnterior;

    // Campo para manejo lógico de eliminación
    @Column(name = "activo")
    private Boolean activo;

    // Relación Muchos a Uno con Cliente (creador del TrabajoIndEnAccion)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Cliente cliente;

    @PrePersist
    protected void onCreate() {
        if (this.estadoTrabajo == null) {
            this.estadoTrabajo = EstadoTrabajo.PENDIENTE;
        }
        this.fechaCambio = LocalDateTime.now();
        this.activo = true;
    }

    public TrabajoEmpEnAccion() {

        this.estadoTrabajoAnterior = estadoTrabajo;
        this.activo = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public TrabajoEmpresa getTrabajoEmpresa() {
        return trabajoEmpresa;
    }

    public void setTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa) {
        this.trabajoEmpresa = trabajoEmpresa;
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
