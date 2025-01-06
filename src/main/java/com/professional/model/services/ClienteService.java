package com.professional.model.services;

import com.professional.model.dto.TrabajoDTO;
import com.professional.model.entities.Cliente;
import com.professional.model.enums.RangoCalificacion;

import java.util.List;

/*
todo la consulta ya esta hecha, solo buscar: 4. Implementar los Métodos en ClienteServiceImpl
 */
public interface ClienteService {

    /**
     * Obtener todos los clientes.
     *
     * @return Lista de Cliente.
     */
    List<Cliente> getAllClientes();

    /**
     * Obtener todos los clientes.
     *
     * @return Lista de Cliente.
     */
    List<Cliente> getAllClientesTodos();
    /**
     * Obtener un Cliente por su ID.
     *
     * @param id ID del Cliente.
     * @return Cliente encontrado.
     */
    Cliente getClienteById(Long id);

    /**
     * Obtener un Cliente por su correo electrónico.
     *
     * @param correo Correo electrónico del Cliente.
     * @return Cliente encontrado.
     */
    Cliente findClienteByCorreo(String correo);

    /**
     * Crear un nuevo Cliente.
     *
     * @param cliente Datos del Cliente a crear.
     * @return Cliente creado.
     */
    Cliente createCliente(Cliente cliente);

    /**
     * Actualizar un Cliente existente.
     *
     * @param id              ID del Cliente a actualizar.
     * @param clienteDetalles Datos actualizados del Cliente.
     * @return Cliente actualizado.
     */
    Cliente updateCliente(Long id, Cliente clienteDetalles);

    /**
     * Eliminar un Cliente por su ID.
     *
     * @param id ID del Cliente a eliminar.
     */
    void deleteCliente(Long id);

    /**
     * Buscar un Cliente por su correo electrónico.
     *
     * @param correo Correo electrónico del Cliente.
     * @return Cliente encontrado.
     */
    Cliente getClienteByCorreo(String correo);

    /**
     * Verificar si existe un Cliente con un correo electrónico específico.
     *
     * @param correo Correo electrónico a verificar.
     * @return true si existe, false de lo contrario.
     */
    boolean existsByCorreo(String correo);

    /**
     * Buscar Clientes por sus nombres (contiene, ignore case).
     *
     * @param nombres Nombres a buscar.
     * @return Lista de Clientes que coinciden.
     */
    List<Cliente> findByNombresContainingIgnoreCase(String nombres);

    /**
     * Buscar Clientes por su estado activo.
     *
     * @param activo Estado activo del Cliente.
     * @return Lista de Clientes que coinciden.
     */
    List<Cliente> findByActivo(Boolean activo);

    /**
     * Lista los trabajos que tienen averageRating entre 0.0 y 3.0.
     *
     * @param descripcion Parte de la descripción a buscar.
     * @return Lista de TrabajoDTO en el rango 0.0 - 3.0.
     */
    List<TrabajoDTO> listarTrabajosPorDescripcionYRangoCalificacion(String descripcion, RangoCalificacion rango);

    /**
     * Lista todos los trabajos independientes y de empresa que coinciden con la descripción,
     * están activos y los ordena por precio ascendente.
     *
     * @param descripcion Parte de la descripción a buscar.
     * @return Lista combinada de TrabajoDTO ordenados por precio ascendente.
     */
    List<TrabajoDTO> listarTrabajosPorDescripcionOrdenadosPorPrecioAsc(String descripcion);

    /**
     * Lista todos los trabajos independientes y de empresa que coinciden con la descripción,
     * están activos y los ordena por precio descendente.
     *
     * @param descripcion Parte de la descripción a buscar.
     * @return Lista combinada de TrabajoDTO ordenados por precio descendente.
     */
    List<TrabajoDTO> listarTrabajosPorDescripcionOrdenadosPorPrecioDesc(String descripcion);

    /**
     * Lista todos los trabajos independientes y de empresa que coinciden con la descripción,
     * están activos y los ordena por fecha de creación ascendente.
     *
     * @param descripcion Parte de la descripción a buscar.
     * @return Lista combinada de TrabajoDTO ordenados por fecha de creación ascendente.
     */
    List<TrabajoDTO> listarTrabajosPorDescripcionOrdenadosPorFechaCreacionAsc(String descripcion);

}
