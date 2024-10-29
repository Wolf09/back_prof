package com.professional.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trabajos_empresa")
public class TrabajoEmpresa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción del trabajo es obligatoria")
    private String trabajo;

    // Nuevo campo para almacenar el promedio de calificaciones
    @Column(name = "average_rating", nullable = false)
    private Double averageRating;

    // Relación Muchos a Uno con Empresa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    @NotNull(message = "La empresa es obligatoria")
    @JsonIgnore
    private Empresa empresa;

    // Relación Muchos a Uno con Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    @JsonIgnore
    private Cliente cliente;

    // Relación Uno a Muchos con HistorialEmpresas
    @OneToMany(mappedBy = "trabajo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<HistorialEmpresas> historialEmpresas;

    // Relación Uno a Muchos con CalificacionEmpresas
    @OneToMany(mappedBy = "trabajo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CalificacionEmpresas> calificaciones;

    // Relación Uno a Muchos con TrabajoEmpEnAccion
    @OneToMany(mappedBy = "trabajoEmpresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TrabajoEmpEnAccion> trabajoEmpEnAccions;

    // Constructor por defecto
    public TrabajoEmpresa() {
        this.historialEmpresas = new ArrayList<>();
        this.calificaciones = new ArrayList<>();
        this.trabajoEmpEnAccions = new ArrayList<>();
        this.averageRating = 5.0; // Establecer promedio inicial en 5.0
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<HistorialEmpresas> getHistorialEmpresas() {
        return historialEmpresas;
    }

    public void setHistorialEmpresas(List<HistorialEmpresas> historialEmpresas) {
        this.historialEmpresas = historialEmpresas;
    }

    public List<CalificacionEmpresas> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionEmpresas> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public List<TrabajoEmpEnAccion> getTrabajoEmpEnAccions() {
        return trabajoEmpEnAccions;
    }

    public void setTrabajoEmpEnAccions(List<TrabajoEmpEnAccion> trabajoEmpEnAccions) {
        this.trabajoEmpEnAccions = trabajoEmpEnAccions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Métodos para gestionar las relaciones

    public void addTrabajoEmpEnAccion(TrabajoEmpEnAccion trabajoEmpEnAccion){
        trabajoEmpEnAccions.add(trabajoEmpEnAccion);
        trabajoEmpEnAccion.setTrabajoEmpresa(this);
    }

    public void removeTrabajoEmpEnAccion(TrabajoEmpEnAccion trabajoEmpEnAccion){
        trabajoEmpEnAccions.remove(trabajoEmpEnAccion);
        trabajoEmpEnAccion.setTrabajoEmpresa(null);
    }

    public void addHistorialEmpresas(HistorialEmpresas historial){
        historialEmpresas.add(historial);
        historial.setTrabajo(this);
    }

    public void removeHistorialEmpresas(HistorialEmpresas historial){
        historialEmpresas.remove(historial);
        historial.setTrabajo(null);
    }

    public void addCalificacionEmpresas(CalificacionEmpresas calificacionEmpresas){
        calificaciones.add(calificacionEmpresas);
        calificacionEmpresas.setTrabajo(this);
    }

    public void removeCalificacionEmpresas(CalificacionEmpresas calificacionEmpresas){
        calificaciones.remove(calificacionEmpresas);
        calificacionEmpresas.setTrabajo(null);
    }

    // Serial UID
    @Serial
    private static final long serialVersionUID = 1L;
}
