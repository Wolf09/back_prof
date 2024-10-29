package com.professional.model.repositories;

import com.professional.model.entities.Cliente;
import com.professional.model.entities.Empresa;
import com.professional.model.entities.TrabajoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajoEmpresaRepository extends JpaRepository<TrabajoEmpresa, Long> {

    /**
     * Buscar trabajos de empresa por nombre.
     *
     * @param trabajo Nombre del trabajo.
     * @return Lista de trabajos que coinciden con el nombre.
     */
    List<TrabajoEmpresa> findByTrabajo(String trabajo);

    /**
     * Buscar trabajos de empresa por empresa.
     *
     * @param empresa Nombre de la empresa.
     * @return Lista de trabajos que coinciden con el nombre.
     */
    List<TrabajoEmpresa> findByEmpresa(Empresa empresa);

    List<TrabajoEmpresa> findByCliente(Cliente cliente);
    /**
     * Buscar un trabajo de empresa por su ID.
     *
     * @param id ID del trabajo de empresa.
     * @return Un Optional que contiene el trabajo si se encuentra, o vacío si no.
     */
    Optional<TrabajoEmpresa> findById(Long id);

    /**
     * Verificar si existe un trabajo de empresa con un nombre específico.
     *
     * @param trabajo Nombre del trabajo.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByTrabajo(String trabajo);

    // Agrega métodos de consulta personalizados aquí si es necesario.
}

