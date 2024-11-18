package com.professional.model.services;

import com.professional.model.entities.CalificacionIndependientes;
import com.professional.model.entities.TrabajoIndEnAccion;
import com.professional.model.entities.TrabajoIndependiente;
import com.professional.model.entities.Cliente;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/*
TODO LA consulta ya esta hecha al chatgpt es la ultima consulta
esta es una nueva consulta a la Basede Datos
1.	Hay detalles que no se están tomando en cuenta la entidad: Independiente, que representa a una persona independiente,
tiene su lista de TrabajoIndependiente, donde puede ver todos sus TrabajoIndependiente y las calificaciones relacionadas a este
TrabajoIndependiente, es decir puede ver quien califico es decir puede ver el Cliente que califico su TrabajoIndependiente.
2.	La entidad CalificacionIndependiente tiene una eliminación física, es decir si se elimina de la base de datos

 */
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


    @Transactional(readOnly = true)
    CalificacionIndependientes getCalificacionByClienteAndTrabajo(Cliente cliente, TrabajoIndEnAccion trabajoIndEnAccion);

    @Transactional(readOnly = true)
    boolean existsCalificacionByClienteAndTrabajo(Cliente cliente, TrabajoIndEnAccion trabajoIndEnAccion);
}
