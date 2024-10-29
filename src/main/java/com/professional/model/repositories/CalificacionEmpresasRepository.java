package com.professional.model.repositories;

import com.professional.model.entities.CalificacionEmpresas;
import com.professional.model.entities.TrabajoEmpresa;
import com.professional.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

@Repository
public interface CalificacionEmpresasRepository extends JpaRepository<CalificacionEmpresas, Long> {

    /**
     * Buscar una calificación por Cliente y TrabajoEmpresa.
     *
     * @param cliente El Cliente que realizó la calificación.
     * @param trabajo El TrabajoEmpresa calificado.
     * @return Optional que contiene la calificación si existe.
     */
    Optional<CalificacionEmpresas> findByClienteAndTrabajo(Cliente cliente, TrabajoEmpresa trabajo);

    /**
     * Verificar si existe una calificación para un Cliente y TrabajoEmpresa específicos.
     *
     * @param cliente El Cliente que realizó la calificación.
     * @param trabajo El TrabajoEmpresa calificado.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByClienteAndTrabajo(Cliente cliente, TrabajoEmpresa trabajo);

    /**
     * Calcular el promedio de calificaciones para un TrabajoEmpresa específico.
     *
     * @param trabajo El TrabajoEmpresa para el cual se calculará el promedio.
     * @return El promedio de calificaciones como Double.
     */
    @Query("SELECT AVG(c.rating) FROM CalificacionEmpresas c WHERE c.trabajo = :trabajo")
    Double findAverageRatingByTrabajo(@Param("trabajo") TrabajoEmpresa trabajo);

    /**
     * Obtener todas las calificaciones asociadas a un TrabajoEmpresa.
     *
     * @param trabajo El TrabajoEmpresa.
     * @return Lista de CalificacionEmpresas.
     */
    List<CalificacionEmpresas> findByTrabajo(TrabajoEmpresa trabajo);
}
