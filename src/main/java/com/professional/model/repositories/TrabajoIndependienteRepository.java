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
     * Verificar si existe un trabajo independiente con un nombre específico.
     *
     * @param trabajo Nombre del trabajo.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByTrabajo(String trabajo);

    // Agrega métodos de consulta personalizados aquí si es necesario.
}

