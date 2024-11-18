package com.professional.model.repositories;

import com.professional.model.entities.CalificacionIndependientes;
import com.professional.model.entities.TrabajoIndEnAccion;
import com.professional.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

@Repository
public interface CalificacionIndependientesRepository extends JpaRepository<CalificacionIndependientes, Long> {

    /**
     * Buscar una calificación por Cliente y TrabajoIndEnAccion, solo si está activa.
     *
     * @param cliente El Cliente que realizó la calificación.
     * @param trabajoIndEnAccion El TrabajoIndEnAccion calificado.
     * @return Optional que contiene la calificación si existe y está activa.
     */
    Optional<CalificacionIndependientes> findByClienteAndTrabajoIndEnAccion(Cliente cliente, TrabajoIndEnAccion trabajoIndEnAccion);

    /**
     * Verificar si existe una calificación para un Cliente y TrabajoIndEnAccion específicos y está activa.
     *
     * @param cliente El Cliente que realizó la calificación.
     * @param trabajoIndEnAccion El TrabajoIndEnAccion calificado.
     * @return true si existe y está activa, false de lo contrario.
     */
    boolean existsByClienteAndTrabajoIndEnAccion(Cliente cliente, TrabajoIndEnAccion trabajoIndEnAccion);

    /**
     * Calcular el promedio de calificaciones para un TrabajoIndEnAccion específico, solo considerando calificaciones activas.
     *
     * @param trabajoIndEnAccion El TrabajoIndEnAccion para el cual se calculará el promedio.
     * @return El promedio de calificaciones como Double.
     */
    @Query("SELECT AVG(c.rating) FROM CalificacionIndependientes c WHERE c.trabajoIndEnAccion = :trabajoIndEnAccion")
    Double findAverageRatingByTrabajoIndEnAccion(@Param("trabajoIndEnAccion") TrabajoIndEnAccion trabajoIndEnAccion);

    /**
     * Obtener todas las calificaciones activas asociadas a un TrabajoIndEnAccion.
     *
     * @param trabajoIndEnAccion El TrabajoIndEnAccion.
     * @return Lista de CalificacionIndependientes activas.
     */
    List<CalificacionIndependientes> findByTrabajoIndEnAccion(TrabajoIndEnAccion trabajoIndEnAccion);
}
