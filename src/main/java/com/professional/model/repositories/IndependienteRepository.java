package com.professional.model.repositories;

import com.professional.model.entities.Independiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndependienteRepository extends JpaRepository<Independiente, Long> {

    /**
     * Buscar un Independiente por su correo electrónico.
     *
     * @param correo El correo electrónico del Independiente.
     * @return Un Optional que contiene el Independiente si se encuentra, o vacío si no.
     */
    Optional<Independiente> findByCorreo(String correo);

    /**
     * Verificar si existe un Independiente con un correo electrónico específico.
     *
     * @param correo El correo electrónico a verificar.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByCorreo(String correo);

    // Agrega métodos de consulta personalizados aquí si es necesario.
}

