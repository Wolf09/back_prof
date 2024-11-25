package com.professional.model.repositories;

import com.professional.model.entities.HistorialIndependientes;
import com.professional.model.entities.TrabajoIndependiente;
import com.professional.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialIndependientesRepository extends JpaRepository<HistorialIndependientes, Long> {

    /**
     * Buscar historiales por TrabajoIndependiente y activo=true.
     *
     * @param trabajo El TrabajoIndependiente.
     * @return Lista de HistorialIndependientes activos.
     */
    List<HistorialIndependientes> findByTrabajoAndActivoTrue(TrabajoIndependiente trabajo);

    /**
     * Buscar historiales por Cliente, TrabajoIndependiente y activo=true.
     *
     * @param cliente El Cliente.
     * @param trabajo El TrabajoIndependiente.
     * @return Lista de HistorialIndependientes activos.
     */
    List<HistorialIndependientes> findByClienteAndTrabajoAndActivoTrue(Cliente cliente, TrabajoIndependiente trabajo);

    /**
     * Obtener todos los historiales, sin filtrar por activo.
     *
     * @return Lista de todos los HistorialIndependientes.
     */
    List<HistorialIndependientes> findAll();

    /**
     * Buscar historiales por Cliente y TrabajoIndependiente, sin filtrar por activo.
     *
     * @param cliente El Cliente.
     * @param trabajo El TrabajoIndependiente.
     * @return Lista de HistorialIndependientes.
     */
    List<HistorialIndependientes> findByClienteAndTrabajo(Cliente cliente, TrabajoIndependiente trabajo);
}
