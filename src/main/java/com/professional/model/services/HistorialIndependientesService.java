package com.professional.model.services;

import com.professional.model.entities.HistorialIndependientes;

import java.util.List;

public interface HistorialIndependientesService {

    /**
     * Obtener todos los Historiales de Independientes.
     *
     * @return Lista de HistorialIndependientes.
     */
    List<HistorialIndependientes> getAllHistorialIndependientes();

    /**
     * Obtener un HistorialIndependientes por su ID.
     *
     * @param id ID del HistorialIndependientes.
     * @return HistorialIndependientes encontrado.
     */
    HistorialIndependientes getHistorialIndependientesById(Long id);

    /**
     * Crear un nuevo HistorialIndependientes.
     *
     * @param historialIndependientes Datos del HistorialIndependientes a crear.
     * @return HistorialIndependientes creado.
     */
    HistorialIndependientes createHistorialIndependientes(HistorialIndependientes historialIndependientes);

    /**
     * Actualizar un HistorialIndependientes existente.
     *
     * @param id                             ID del HistorialIndependientes a actualizar.
     * @param historialIndependientesDetalles Datos actualizados del HistorialIndependientes.
     * @return HistorialIndependientes actualizado.
     */
    HistorialIndependientes updateHistorialIndependientes(Long id, HistorialIndependientes historialIndependientesDetalles);

    /**
     * Eliminar un HistorialIndependientes por su ID.
     *
     * @param id ID del HistorialIndependientes a eliminar.
     */
    void deleteHistorialIndependientes(Long id);
}

