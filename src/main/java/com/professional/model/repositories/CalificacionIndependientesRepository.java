package com.professional.model.repositories;

import com.professional.model.entities.CalificacionIndependientes;
import com.professional.model.entities.TrabajoIndependiente;
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
     * Buscar una calificación por Cliente y TrabajoIndependiente.
     *
     * @param cliente El Cliente que realizó la calificación.
     * @param trabajo El TrabajoIndependiente calificado.
     * @return Optional que contiene la calificación si existe.
     */
    Optional<CalificacionIndependientes> findByClienteAndTrabajo(Cliente cliente, TrabajoIndependiente trabajo);

    /**
     * Verificar si existe una calificación para un Cliente y TrabajoIndependiente específicos.
     *
     * @param cliente El Cliente que realizó la calificación.
     * @param trabajo El TrabajoIndependiente calificado.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByClienteAndTrabajo(Cliente cliente, TrabajoIndependiente trabajo);

    /**
     * Calcular el promedio de calificaciones para un TrabajoIndependiente específico.
     *
     * @param trabajo El TrabajoIndependiente para el cual se calculará el promedio.
     * @return El promedio de calificaciones como Double.
     */
    @Query("SELECT AVG(c.rating) FROM CalificacionIndependientes c WHERE c.trabajo = :trabajo")
    Double findAverageRatingByTrabajo(@Param("trabajo") TrabajoIndependiente trabajo);

    /**
     * Obtener todas las calificaciones asociadas a un TrabajoIndependiente.
     *
     * @param trabajo El TrabajoIndependiente.
     * @return Lista de CalificacionIndependientes.
     */
    List<CalificacionIndependientes> findByTrabajo(TrabajoIndependiente trabajo);
}
