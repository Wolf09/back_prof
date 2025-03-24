package com.professional.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class TrabajoIndependienteDTO {

    private Long idIndependiente;

    private Long idTrabIndependiente;
    private String profesion;
    private String nombres;
    private String apellidos;

    private String celular;

    private String direccion;

    @Column(name = "foto_representante")
    private String fotoRepresentante;
    @Column(name = "carta_presentacion")
    private String cartaPresentacion;
    private String mision;
    private String vision;
    private String pais;
    private String ciudad;
    @Column(name = "area_trabajo")
    private String areaTrabajo;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Column(name = "descripcion_corta")
    private String descripcionCorta;
    @NotBlank(message = "La descripción del trabajo es obligatoria")
    private String descripcion;
    @Column(name = "average_rating")
    private Double averageRating;
    @Column(name = "precio")
    private Double precio;
    @Column(name = "ventas")
    private Long ventas;
    @Column(name = "foto_Titulo")
    private String fotoTitulo;

    public TrabajoIndependienteDTO(Long idIndependiente, Long idTrabIndependiente, String profesion, String nombres,
                                   String apellidos, String cartaPresentacion, String mision, String vision,
                                   String pais, String ciudad, String areaTrabajo, LocalDateTime fechaCreacion,
                                   String descripcionCorta, String descripcion, Double averageRating, Double precio,
                                   Long ventas, String fotoTitulo, String celular, String fotoRepresentante, String direccion) {
        this.idIndependiente = idIndependiente;
        this.idTrabIndependiente = idTrabIndependiente;
        this.profesion = profesion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cartaPresentacion = cartaPresentacion;
        this.mision = mision;
        this.vision = vision;
        this.pais = pais;
        this.ciudad = ciudad;
        this.areaTrabajo = areaTrabajo;
        this.fechaCreacion = fechaCreacion;
        this.descripcionCorta = descripcionCorta;
        this.descripcion = descripcion;
        this.averageRating = averageRating;
        this.precio = precio;
        this.ventas = ventas;
        this.fotoTitulo = fotoTitulo;
        this.celular = celular;
        this.fotoRepresentante = fotoRepresentante;
        this.direccion=direccion;
    }

    public TrabajoIndependienteDTO() {
    }

    public Long getIdIndependiente() {
        return idIndependiente;
    }

    public void setIdIndependiente(Long idIndependiente) {
        this.idIndependiente = idIndependiente;
    }

    public Long getIdTrabIndependiente() {
        return idTrabIndependiente;
    }

    public void setIdTrabIndependiente(Long idTrabIndependiente) {
        this.idTrabIndependiente = idTrabIndependiente;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
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

    public String getCartaPresentacion() {
        return cartaPresentacion;
    }

    public void setCartaPresentacion(String cartaPresentacion) {
        this.cartaPresentacion = cartaPresentacion;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getAreaTrabajo() {
        return areaTrabajo;
    }

    public void setAreaTrabajo(String areaTrabajo) {
        this.areaTrabajo = areaTrabajo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public Long getVentas() {
        return ventas;
    }

    public void setVentas(Long ventas) {
        this.ventas = ventas;
    }

    public String getFotoTitulo() {
        return fotoTitulo;
    }

    public void setFotoTitulo(String fotoTitulo) {
        this.fotoTitulo = fotoTitulo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFotoRepresentante() {
        return fotoRepresentante;
    }

    public void setFotoRepresentante(String fotoRepresentante) {
        this.fotoRepresentante = fotoRepresentante;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
