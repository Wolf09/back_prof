package com.professional.model.services;

import com.professional.model.dto.HistorialDTO;
import com.professional.model.entities.HistorialIndependientes;

import java.util.List;

public interface HistorialIndependientesService {

    /**
     * Obtener todos los Historiales de Independientes activos.
     *
     * @return Lista de HistorialIndependientes activos.
     */
    List<HistorialIndependientes> getAllHistorialIndependientes();

    /**
     * Obtener todos los Historiales de Independientes, incluyendo inactivos.
     *
     * @return Lista de todos los HistorialIndependientes.
     */
    List<HistorialIndependientes> getAllHistorialIndependientesIncludingInactive();

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
     * Eliminar (lógicamente) un HistorialIndependientes por su ID.
     *
     * @param id ID del HistorialIndependientes a eliminar.
     */
    void deleteHistorialIndependientes(Long id);

    /**
     * Buscar HistorialIndependientes por Cliente y TrabajoIndependiente activos.
     *
     * @param clienteId ID del Cliente.
     * @param trabajoId ID del TrabajoIndependiente.
     * @return Lista de HistorialIndependientes activos encontrados.
     */
    List<HistorialIndependientes> findByClienteAndTrabajo(Long clienteId, Long trabajoId);
}
