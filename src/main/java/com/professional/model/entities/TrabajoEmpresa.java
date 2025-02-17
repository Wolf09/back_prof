package com.professional.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trabajos_empresa")
public class TrabajoEmpresa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción del trabajo es obligatoria")
    private String descripcion;

    // almacena el promedio de las calificaciones recibidas para este trabajo
    @Column(name = "average_rating", nullable = false)
    private Double averageRating;

    // Nuevo campo para manejo lógico de eliminación
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "ventas", nullable = false)
    private Long ventas;
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

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
    }
    @PrePersist
    public void establecerAverage(){

        this.averageRating = 5.0;
        this.activo=true;
        this.ventas=0L;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y Setters

    public Long getId() {
        return id;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // Serial UID
    @Serial
    private static final long serialVersionUID = 1L;
}
