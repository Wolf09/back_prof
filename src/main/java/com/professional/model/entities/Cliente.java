package com.professional.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 60, message = "El nombre debe tener entre 2 y 60 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 60, message = "Los apellidos deben tener entre 2 y 60 caracteres")
    private String apellidos;

    @NotBlank(message = "El celular es obligatorio")
    @Pattern(regexp = "^(1|[5-9][0-9]{2})\\d{7,10}$", message = "El celular debe tener un formato válido, por ejemplo: 59170735671")
    private String celular;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 60, message = "La contraseña debe tener entre 8 y 60 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,60}$",
            message = "La contraseña debe incluir al menos una letra mayúscula, un número y un carácter especial")
    @Column(length = 200) // Para acomodar el hash de BCrypt
    private String password;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @Column(name = "fecha_apertura", nullable = false, updatable = false)
    private LocalDateTime fechaApertura;

    @NotNull
    private Boolean activo;

    // Relaciones con otras entidades
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<HistorialIndependientes> historialIndependientes;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CalificacionIndependientes> calificacionIndependientes;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<HistorialEmpresas> historialEmpresas;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CalificacionEmpresas> calificacionEmpresas;

    // Constructor por defecto
    public Cliente() {
        this.historialIndependientes = new ArrayList<>();
        this.calificacionIndependientes = new ArrayList<>();
        this.historialEmpresas = new ArrayList<>();
        this.calificacionEmpresas = new ArrayList<>();
    }

    @PrePersist
    protected void onCreate() {
        this.activo = true;
        this.fechaApertura = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDateTime fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<HistorialIndependientes> getHistorialIndependientes() {
        return historialIndependientes;
    }

    public void setHistorialIndependientes(List<HistorialIndependientes> historialIndependientes) {
        this.historialIndependientes = historialIndependientes;
    }

    public List<CalificacionIndependientes> getCalificacionIndependientes() {
        return calificacionIndependientes;
    }

    public void setCalificacionIndependientes(List<CalificacionIndependientes> calificacionIndependientes) {
        this.calificacionIndependientes = calificacionIndependientes;
    }

    public List<HistorialEmpresas> getHistorialEmpresas() {
        return historialEmpresas;
    }

    public void setHistorialEmpresas(List<HistorialEmpresas> historialEmpresas) {
        this.historialEmpresas = historialEmpresas;
    }

    public List<CalificacionEmpresas> getCalificacionEmpresas() {
        return calificacionEmpresas;
    }

    public void setCalificacionEmpresas(List<CalificacionEmpresas> calificacionEmpresas) {
        this.calificacionEmpresas = calificacionEmpresas;
    }

    public List<HistorialIndependientes> gethistorialIndependientes() {
        return historialIndependientes;
    }

    public void sethistorialIndependientes(List<HistorialIndependientes> historialIndependientes) {
        this.historialIndependientes = historialIndependientes;
    }

    // Métodos para gestionar la relación con HistorialIndependientes
    public void addHistorialIndependientes (HistorialIndependientes historial){
        historialIndependientes.add(historial);
        historial.setCliente(this);
    }
    public void removeHistorialIndependientes (HistorialIndependientes historial){
        historialIndependientes.remove(historial);
        historial.setCliente(null);
    }

    // Métodos para gestionar la relación con CalificacionIndependientes
    public void addCalificacionIndependientes(CalificacionIndependientes calificacion){
        calificacionIndependientes.add(calificacion);
        calificacion.setCliente(this);
    }

    public void removeCalificacionIndependientes(CalificacionIndependientes calificacion){
        calificacionIndependientes.remove(calificacion);
        calificacion.setCliente(null);
    }

    // Métodos para gestionar la relación con HistorialEmpresas
    public void addHistorialEmpresas(HistorialEmpresas historial){
        historialEmpresas.add(historial);
        historial.setCliente(this);
    }
    public void removeHistorialEmpresas(HistorialEmpresas historial){
        historialEmpresas.remove(historial);
        historial.setCliente(null);
    }

    // Métodos para gestionar la relación con CalificacionEmpresas
    public void addCalificacionEmpresas(CalificacionEmpresas calificacion){
        calificacionEmpresas.add(calificacion);
        calificacion.setCliente(this);
    }
    public void removeCalificacionEmpresas(CalificacionEmpresas calificacion){
        calificacionEmpresas.remove(calificacion);
        calificacion.setCliente(null);
    }


    @Serial
    private static final long serialVersionUID = 1L;
}
