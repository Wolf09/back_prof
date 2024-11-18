package com.professional.model.repositories;
import com.professional.model.entities.TrabajoIndependiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajoIndependienteRepository extends JpaRepository<TrabajoIndependiente, Long> {

    /**
     * Buscar trabajos independientes por nombre.
     *
     * @param trabajo Nombre del trabajo.
     * @return Lista de trabajos que coinciden con el nombre.
     */
    List<TrabajoIndependiente> findByTrabajo(String trabajo);

    /**
     * Buscar un trabajo independiente por su ID.
     *
     * @param id ID del trabajo independiente.
     * @return Un Optional que contiene el trabajo si se encuentra, o vacío si no.
     */
    Optional<TrabajoIndependiente> findById(Long id);

    /**
     * Buscar trabajos de empresa que están activos.
     *
     * @param activo Estado de actividad.
     * @return Lista de trabajos que coinciden con el estado.
     */
    List<TrabajoIndependiente> findByActivo(Boolean activo);

    /**
     * Buscar un TrabajoIndependiente por su ID y que esté activo.
     *
     * @param id ID del TrabajoIndependiente.
     * @return Optional de TrabajoIndependiente.
     */
    Optional<TrabajoIndependiente> findByIdAndActivoTrue(Long id);
    /**
     * Verificar si existe un trabajo independiente con un nombre específico.
     *
     * @param trabajo Nombre del trabajo.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByTrabajo(String trabajo);

    // Agrega métodos de consulta personalizados aquí si es necesario.
}

