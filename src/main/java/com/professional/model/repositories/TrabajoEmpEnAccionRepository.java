package com.professional.model.repositories;

import com.professional.model.entities.TrabajoEmpEnAccion;
import com.professional.model.entities.TrabajoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrabajoEmpEnAccionRepository extends JpaRepository<TrabajoEmpEnAccion, Long> {

    /**
     * Obtener todas las acciones de un trabajo de empresa específico.
     *
     * @param trabajoEmpresa Trabajo de empresa del cual se desean las acciones.
     * @return Lista de acciones asociadas al trabajo de empresa.
     */
    List<TrabajoEmpEnAccion> findByTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa);

    // Agrega métodos de consulta personalizados aquí si es necesario.
}

