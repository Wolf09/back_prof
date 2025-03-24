package com.professional.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

// TODO basarse en TrabajoIndependienteDTO y hacer esta clase y todo su flujo
public class TrabajoEmpresaDTO {
    private Long idEmpresa;

    private Long idTrabEmpresa;
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
    @Column(name = "nombre_empresa")
    private String nombreEmpresa;

    @Column(name = "registro_empresa")
    private String registroDeEmpresa;
    @Column(name = "licencia_comercial")
    private String licenciaComercial;

    @Column(name = "foto_Titulo")
    private String fotoTitulo;
    @Column(name = "area_trabajo")
    private String areaTrabajo;

    @Column(name = "descripcion_corta")
    private String descripcionCorta;

    private String descripcion;

    // almacena el promedio de las calificaciones recibidas para este trabajo
    @Column(name = "average_rating")
    private Double averageRating;

    // Nuevo campo para manejo lógico de eliminación
    @Column(name = "activo")
    private Boolean activo;
    @Column(name = "precio")
    private Double precio;

    @Column(name = "ventas")
    private Long ventas;

    public TrabajoEmpresaDTO(Long idEmpresa, Long idTrabEmpresa, String nombres, String apellidos,
                             String celular, String direccion, String fotoRepresentante, String cartaPresentacion,
                             String mision, String vision, String pais, String ciudad, String nombreEmpresa,
                             String registroDeEmpresa, String licenciaComercial, String fotoTitulo, String areaTrabajo,
                             String descripcionCorta, String descripcion, Double averageRating, Boolean activo, Double precio, Long ventas) {
        this.idEmpresa = idEmpresa;
        this.idTrabEmpresa = idTrabEmpresa;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.celular = celular;
        this.direccion = direccion;
        this.fotoRepresentante = fotoRepresentante;
        this.cartaPresentacion = cartaPresentacion;
        this.mision = mision;
        this.vision = vision;
        this.pais = pais;
        this.ciudad = ciudad;
        this.nombreEmpresa = nombreEmpresa;
        this.registroDeEmpresa = registroDeEmpresa;
        this.licenciaComercial = licenciaComercial;
        this.fotoTitulo = fotoTitulo;
        this.areaTrabajo = areaTrabajo;
        this.descripcionCorta = descripcionCorta;
        this.descripcion = descripcion;
        this.averageRating = averageRating;
        this.activo = activo;
        this.precio = precio;
        this.ventas = ventas;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getIdTrabEmpresa() {
        return idTrabEmpresa;
    }

    public void setIdTrabEmpresa(Long idTrabEmpresa) {
        this.idTrabEmpresa = idTrabEmpresa;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFotoRepresentante() {
        return fotoRepresentante;
    }

    public void setFotoRepresentante(String fotoRepresentante) {
        this.fotoRepresentante = fotoRepresentante;
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

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getRegistroDeEmpresa() {
        return registroDeEmpresa;
    }

    public void setRegistroDeEmpresa(String registroDeEmpresa) {
        this.registroDeEmpresa = registroDeEmpresa;
    }

    public String getLicenciaComercial() {
        return licenciaComercial;
    }

    public void setLicenciaComercial(String licenciaComercial) {
        this.licenciaComercial = licenciaComercial;
    }

    public String getFotoTitulo() {
        return fotoTitulo;
    }

    public void setFotoTitulo(String fotoTitulo) {
        this.fotoTitulo = fotoTitulo;
    }

    public String getAreaTrabajo() {
        return areaTrabajo;
    }

    public void setAreaTrabajo(String areaTrabajo) {
        this.areaTrabajo = areaTrabajo;
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
}
