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
     * Buscar historiales por TrabajoIndependiente.
     *
     * @param trabajo El TrabajoIndependiente.
     * @return Lista de HistorialIndependientes.
     */
    List<HistorialIndependientes> findByTrabajo(TrabajoIndependiente trabajo);

    /**
     * Buscar historiales por Cliente y TrabajoIndependiente.
     *
     * @param cliente El Cliente.
     * @param trabajo El TrabajoIndependiente.
     * @return Lista de HistorialIndependientes.
     */
    List<HistorialIndependientes> findByClienteAndTrabajo(Cliente cliente, TrabajoIndependiente trabajo);
}


