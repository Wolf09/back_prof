package com.professional.model.services;

import com.professional.model.entities.EstadoTrabajo;
import com.professional.model.entities.TrabajoIndEnAccion;
import com.professional.model.entities.TrabajoIndependiente;

import java.util.List;

public interface TrabajoIndEnAccionService {

    /**
     * Obtener todas las acciones de trabajos independientes.
     *
     * @return Lista de TrabajoIndEnAccion.
     */
    List<TrabajoIndEnAccion> getAllTrabajosEnAccion();

    /**
     * Obtener una TrabajoIndEnAccion por su ID.
     *
     * @param id ID de la TrabajoIndEnAccion.
     * @return TrabajoIndEnAccion encontrada.
     */
    TrabajoIndEnAccion getTrabajoEnAccionById(Long id);


    /**
     * Actualizar el estadoTrabajo de un TrabajoIndEnAccion existente.
     *
     * @param id ID del TrabajoIndEnAccion a actualizar.
     * @param estadoTrabajo Nuevo estadoTrabajo.
     * @return TrabajoIndEnAccion actualizado.
     */
    TrabajoIndEnAccion updateEstadoTrabajo(Long id, EstadoTrabajo estadoTrabajo);
    /**
     * Crear una nueva TrabajoIndEnAccion.
     *
     * @param trabajoEnAccion Datos de la TrabajoIndEnAccion a crear.
     * @return TrabajoIndEnAccion creada.
     */
    TrabajoIndEnAccion createTrabajoEnAccion(TrabajoIndEnAccion trabajoEnAccion);

    /**
     * Actualizar una TrabajoIndEnAccion existente.
     *
     * @param id                    ID de la TrabajoIndEnAccion a actualizar.
     * @param trabajoEnAccionDetalles Datos actualizados de la TrabajoIndEnAccion.
     * @return TrabajoIndEnAccion actualizada.
     */
    TrabajoIndEnAccion updateTrabajoEnAccion(Long id, TrabajoIndEnAccion trabajoEnAccionDetalles);

    /**
     * Eliminar una TrabajoIndEnAccion por su ID.
     *
     * @param id ID de la TrabajoIndEnAccion a eliminar.
     */
    void deleteTrabajoEnAccion(Long id);

    /**
     * Obtener todas las acciones asociadas a un TrabajoIndependiente específico.
     *
     * @param trabajoIndependiente TrabajoIndependiente del cual se desean las acciones.
     * @return Lista de TrabajoIndEnAccion asociadas.
     */
    List<TrabajoIndEnAccion> getTrabajosEnAccionByTrabajoIndependiente(TrabajoIndependiente trabajoIndependiente);
}

