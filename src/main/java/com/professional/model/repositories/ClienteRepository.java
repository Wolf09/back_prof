package com.professional.model.repositories;

import com.professional.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Buscar un cliente por su correo electrónico y estado activo.
     *
     * @param correo Correo electrónico del cliente.
     * @param activo Estado activo del cliente.
     * @return Un Optional que contiene el cliente si se encuentra y está activo, o vacío si no.
     */
    Optional<Cliente> findByCorreoAndActivo(String correo, Boolean activo);

    /**
     * Buscar un cliente por su ID y estado activo.
     *
     * @param id     ID del cliente.
     * @param activo Estado activo del cliente.
     * @return Un Optional que contiene el cliente si se encuentra y está activo, o vacío si no.
     */
    Optional<Cliente> findByIdAndActivo(Long id, Boolean activo);

    /**
     * Buscar clientes por su estado activo.
     *
     * @param activo Estado activo del cliente.
     * @return Lista de clientes que coinciden con el estado proporcionado.
     */
    List<Cliente> findByActivo(Boolean activo);

    /**
     * Buscar un cliente por su correo electrónico.
     *
     * @param correo El correo electrónico del cliente.
     * @return Un Optional que contiene el cliente si se encuentra, o vacío si no.
     */
    Optional<Cliente> findByCorreoAndActivoTrue(String correo);

    /**
     * Verificar si existe un cliente con un correo electrónico específico.
     *
     * @param correo El correo electrónico a verificar.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByCorreo(String correo);

    /**
     * Verificar si existe un cliente con un correo electrónico específico.
     *
     * @param correo El correo electrónico a verificar.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByCorreoAndActivo(String correo, Boolean activo);
    /**
     * Buscar clientes por su nombre.
     *
     * @param nombres Los nombres del cliente.
     * @return Lista de clientes que coinciden con los nombres proporcionados.
     */
    List<Cliente> findByNombresContainingIgnoreCaseAndActivo(String nombres,Boolean activo);

    // Agrega métodos de consulta personalizados aquí si es necesario.
}
