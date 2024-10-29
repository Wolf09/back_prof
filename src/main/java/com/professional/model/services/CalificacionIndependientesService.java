package com.professional.model.services;

import com.professional.model.entities.CalificacionIndependientes;
import com.professional.model.entities.TrabajoIndependiente;
import com.professional.model.entities.Cliente;

import java.util.List;

public interface CalificacionIndependientesService {

    /**
     * Obtener todas las calificaciones.
     *
     * @return Lista de CalificacionIndependientes.
     */
    List<CalificacionIndependientes> getAllCalificaciones();

    /**
     * Obtener una CalificacionIndependientes por su ID.
     *
     * @param id ID de la CalificacionIndependientes.
     * @return CalificacionIndependientes encontrada.
     */
    CalificacionIndependientes getCalificacionById(Long id);

    /**
     * Crear una nueva CalificacionIndependientes.
     *
     * @param calificacion Datos de la CalificacionIndependientes a crear.
     * @return CalificacionIndependientes creada.
     */
    CalificacionIndependientes createCalificacion(CalificacionIndependientes calificacion);

    /**
     * Actualizar una CalificacionIndependientes existente.
     *
     * @param id                ID de la CalificacionIndependientes a actualizar.
     * @param calificacionDetalles Datos actualizados de la CalificacionIndependientes.
     * @return CalificacionIndependientes actualizada.
     */
    CalificacionIndependientes updateCalificacion(Long id, CalificacionIndependientes calificacionDetalles);

    /**
     * Eliminar una CalificacionIndependientes por su ID.
     *
     * @param id ID de la CalificacionIndependientes a eliminar.
     */
    void deleteCalificacion(Long id);

    /**
     * Obtener una CalificacionIndependientes por Cliente y TrabajoIndependiente.
     *
     * @param cliente          Cliente que realizó la calificación.
     * @param trabajoIndependiente TrabajoIndependiente calificado.
     * @return CalificacionIndependientes encontrada.
     */
    CalificacionIndependientes getCalificacionByClienteAndTrabajo(Cliente cliente, TrabajoIndependiente trabajoIndependiente);

    /**
     * Verificar si existe una CalificacionIndependientes para un Cliente y TrabajoIndependiente específicos.
     *
     * @param cliente          Cliente que realizó la calificación.
     * @param trabajoIndependiente TrabajoIndependiente calificado.
     * @return true si existe, false de lo contrario.
     */
    boolean existsCalificacionByClienteAndTrabajo(Cliente cliente, TrabajoIndependiente trabajoIndependiente);
}
