package com.professional.model.repositories;

import com.professional.model.entities.Independiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndependienteRepository extends JpaRepository<Independiente, Long> {

    /**
     * Buscar un Independiente por su correo electrónico.
     *
     * @param correo El correo electrónico del Independiente.
     * @return Un Optional que contiene el Independiente si se encuentra, o vacío si no.
     */
    Optional<Independiente> findByCorreoAndActivoTrue(String correo);

    /**
     * Verificar si existe un Independiente con un correo electrónico específico.
     *
     * @param correo El correo electrónico a verificar.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByCorreoAndActivoTrue(String correo);

    // Método para encontrar todos los Independientes activos
    List<Independiente> findByActivoTrue();

    Optional<Independiente> findByIdAndActivoTrue(Long id);

    // Método para encontrar todos los Independientes (independientemente de activo)
    List<Independiente> findAll();
}

