package com.professional.model.repositories;

import com.professional.model.entities.TrabajoIndEnAccion;
import com.professional.model.entities.TrabajoIndependiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrabajoIndEnAccionRepository extends JpaRepository<TrabajoIndEnAccion, Long> {

    /**
     * Obtener todas las acciones de un trabajo independiente específico.
     *
     * @param trabajoIndependiente Trabajo independiente del cual se desean las acciones.
     * @return Lista de acciones asociadas al trabajo independiente.
     */
    List<TrabajoIndEnAccion> findByTrabajoIndependiente(TrabajoIndependiente trabajoIndependiente);

    // Agrega métodos de consulta personalizados aquí si es necesario.
}

