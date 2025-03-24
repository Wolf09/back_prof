package com.professional.model.services;

import com.professional.model.dto.*;
import com.professional.model.entities.Cliente;
import com.professional.model.entities.TrabajoEmpresa;
import com.professional.model.entities.TrabajoIndependiente;
import com.professional.model.enums.RangoCalificacion;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.ClienteRepository;
import com.professional.model.repositories.TrabajoEmpresaRepository;
import com.professional.model.repositories.TrabajoIndependienteRepository;
import com.professional.model.dto.FiltroTrabajoEmpresaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// todo Buscar el dudas backend profesional.word que esta en el escritorio
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final TrabajoIndependienteRepository trabajoIndependienteRepository;
    private final TrabajoEmpresaRepository trabajoEmpresaRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository,
                              PasswordEncoder passwordEncoder,
                              TrabajoIndependienteRepository trabajoIndependienteRepository,
                              TrabajoEmpresaRepository trabajoEmpresaRepository) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.trabajoIndependienteRepository = trabajoIndependienteRepository;
        this.trabajoEmpresaRepository = trabajoEmpresaRepository;
    }

    /**
     * {@inheritDoc}
     * Retorna solo clientes activos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getAllClientes() {
        return clienteRepository.findByActivo(true);
    }

    /**
     * {@inheritDoc}
     * Retorna todos los clientes, incluyendo inactivos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getAllClientesTodos() {
        return clienteRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Cliente getClienteById(Long id) {
        return clienteRepository.findByIdAndActivo(id,true)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado o dado de Baja con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findClienteByCorreo(String correo) {
        return clienteRepository.findByCorreoAndActivo(correo,true)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado o dado de Baja con correo: " + correo));
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findByCorreo(String correo) {
        return clienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con correo: " + correo));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Cliente createCliente(Cliente cliente) {
        // Validaciones adicionales pueden agregarse aquí
        // Por ejemplo, verificar si el correo ya existe
        if (clienteRepository.existsByCorreo(cliente.getCorreo())) {
            throw new IllegalStateException("El correo electrónico ya está en uso.");
        }
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        cliente.setActivo(true); // Asegurar que el cliente sea activo al crear
        return clienteRepository.save(cliente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Cliente updateCliente(Long id, Cliente clienteDetalles) {
        Cliente existente = clienteRepository.findByIdAndActivo(id,true)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado o dado de Baja con ID: " + id));

        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Verificar que el usuario autenticado es el propietario de la cuenta
        if (!existente.getCorreo().equals(currentUsername)) {
            throw new AccessDeniedException("No tienes permiso para actualizar esta cuenta");
        }

        // Actualizar campos existentes
        existente.setNombres(clienteDetalles.getNombres());
        existente.setApellidos(clienteDetalles.getApellidos());
        existente.setCelular(clienteDetalles.getCelular());
        existente.setCorreo(clienteDetalles.getCorreo());
        existente.setActivo(clienteDetalles.getActivo());
        existente.setPais(clienteDetalles.getPais());
        existente.setCiudad(clienteDetalles.getCiudad());
        existente.setDireccion(clienteDetalles.getDireccion());

        // Si se actualiza la contraseña, encriptarla
        if (clienteDetalles.getPassword() != null && !clienteDetalles.getPassword().isEmpty()) {
            existente.setPassword(passwordEncoder.encode(clienteDetalles.getPassword()));
        }

        return clienteRepository.save(existente);
    }

    /**
     * {@inheritDoc}
     * Realiza una eliminación lógica cambiando 'activo' a false.
     */
    @Override
    @Transactional
    public void deleteCliente(Long id) {
        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        existente.setActivo(false);
        clienteRepository.save(existente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Cliente getClienteByCorreo(String correo) {
        return clienteRepository.findByCorreoAndActivo(correo,true)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado o dado de Baja con correo: " + correo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByCorreo(String correo) {
        return clienteRepository.existsByCorreoAndActivo(correo,true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findByNombresContainingIgnoreCase(String nombres) {
        return clienteRepository.findByNombresContainingIgnoreCaseAndActivo(nombres,true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findByActivo(Boolean activo) {
        return clienteRepository.findByActivo(activo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoDTO> listarTrabajosPorDescripcionYRangoCalificacion(String descripcion, RangoCalificacion rango) {
        // Validación de entrada
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        if (rango == null) {
            throw new IllegalArgumentException("El rango de calificación no puede ser nulo.");
        }

        // Determinar los límites del rango
        Double minRating;
        Double maxRating;

        switch (rango) {
            case RANGO_0_3:
                minRating = 0.0;
                maxRating = 3.0;
                break;
            case RANGO_3_3_5:
                minRating = 3.0;
                maxRating = 3.5;
                break;
            case RANGO_3_5_4_0:
                minRating = 3.5;
                maxRating = 4.0;
                break;
            case RANGO_4_0_4_5:
                minRating = 4.0;
                maxRating = 4.5;
                break;
            case RANGO_4_5_5_0:
                minRating = 4.5;
                maxRating = 5.0;
                break;
            default:
                throw new IllegalArgumentException("Rango de calificación no soportado.");
        }

        // Buscar trabajos independientes dentro del rango
        List<TrabajoIndependiente> trabajosIndependientes = trabajoIndependienteRepository
                .findByDescripcionContainingIgnoreCaseAndActivoTrueAndAverageRatingBetween(descripcion, minRating, maxRating);

        // Buscar trabajos de empresas dentro del rango
        List<TrabajoEmpresa> trabajosEmpresas = trabajoEmpresaRepository
                .findByDescripcionContainingIgnoreCaseAndActivoTrueAndAverageRatingBetween(descripcion, minRating, maxRating);

        // Convertir a TrabajoDTO y asignar tipoTrabajo
        List<TrabajoDTO> dtoIndependientes = trabajosIndependientes.stream()
                .map(t -> new TrabajoDTO(
                        t.getId(),
                        t.getDescripcion(),
                        t.getAverageRating(),
                        t.getActivo(),
                        t.getPrecio(),
                        t.getFechaCreacion(),
                        "Independiente"
                ))
                .collect(Collectors.toList());

        List<TrabajoDTO> dtoEmpresas = trabajosEmpresas.stream()
                .map(t -> new TrabajoDTO(
                        t.getId(),
                        t.getDescripcion(),
                        t.getAverageRating(),
                        t.getActivo(),
                        t.getPrecio(),
                        t.getFechaCreacion(),
                        "Empresa"
                ))
                .collect(Collectors.toList());

        // Combinar las listas en una nueva lista para evitar modificar las listas originales
        List<TrabajoDTO> trabajosCombinados = new ArrayList<>();
        trabajosCombinados.addAll(dtoIndependientes);
        trabajosCombinados.addAll(dtoEmpresas);

        // Ordenar por averageRating descendente
        trabajosCombinados.sort(Comparator.comparing(TrabajoDTO::getAverageRating).reversed());

        return trabajosCombinados;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoDTO> listarTrabajosPorDescripcionOrdenadosPorPrecioAsc(String descripcion) {

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        // Buscar trabajos independientes
        List<TrabajoIndependiente> trabajosIndependientes = trabajoIndependienteRepository
                .findByDescripcionContainingIgnoreCaseAndActivoTrue(descripcion);

        // Buscar trabajos de empresas
        List<TrabajoEmpresa> trabajosEmpresas = trabajoEmpresaRepository
                .findByDescripcionContainingIgnoreCaseAndActivoTrue(descripcion);

        // Convertir a TrabajoDTO y asignar tipoTrabajo
        List<TrabajoDTO> dtoIndependientes = trabajosIndependientes.stream()
                .map(t -> new TrabajoDTO(
                        t.getId(),
                        t.getDescripcion(),
                        t.getAverageRating(),
                        t.getActivo(),
                        t.getPrecio(),
                        t.getFechaCreacion(),
                        "Independiente"
                ))
                .collect(Collectors.toList());

        List<TrabajoDTO> dtoEmpresas = trabajosEmpresas.stream()
                .map(t -> new TrabajoDTO(
                        t.getId(),
                        t.getDescripcion(),
                        t.getAverageRating(),
                        t.getActivo(),
                        t.getPrecio(),
                        t.getFechaCreacion(),
                        "Empresa"
                ))
                .collect(Collectors.toList());

        // Combinar las listas
        List<TrabajoDTO> trabajosCombinados = dtoIndependientes;
        trabajosCombinados.addAll(dtoEmpresas);

        // Ordenar por precio ascendente
        trabajosCombinados.sort(Comparator.comparing(TrabajoDTO::getPrecio));

        return trabajosCombinados;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoDTO> listarTrabajosPorDescripcionOrdenadosPorPrecioDesc(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        // Buscar trabajos independientes
        List<TrabajoIndependiente> trabajosIndependientes = trabajoIndependienteRepository
                .findByDescripcionContainingIgnoreCaseAndActivoTrue(descripcion);

        // Buscar trabajos de empresas
        List<TrabajoEmpresa> trabajosEmpresas = trabajoEmpresaRepository
                .findByDescripcionContainingIgnoreCaseAndActivoTrue(descripcion);

        // Convertir a TrabajoDTO y asignar tipoTrabajo
        List<TrabajoDTO> dtoIndependientes = trabajosIndependientes.stream()
                .map(t -> new TrabajoDTO(
                        t.getId(),
                        t.getDescripcion(),
                        t.getAverageRating(),
                        t.getActivo(),
                        t.getPrecio(),
                        t.getFechaCreacion(),
                        "Independiente"
                ))
                .collect(Collectors.toList());

        List<TrabajoDTO> dtoEmpresas = trabajosEmpresas.stream()
                .map(t -> new TrabajoDTO(
                        t.getId(),
                        t.getDescripcion(),
                        t.getAverageRating(),
                        t.getActivo(),
                        t.getPrecio(),
                        t.getFechaCreacion(),
                        "Empresa"
                ))
                .collect(Collectors.toList());

        // Combinar las listas
        List<TrabajoDTO> trabajosCombinados = dtoIndependientes;
        trabajosCombinados.addAll(dtoEmpresas);

        // Ordenar por precio descendente
        trabajosCombinados.sort((t1, t2) -> t2.getPrecio().compareTo(t1.getPrecio()));

        return trabajosCombinados;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoDTO> listarTrabajosPorDescripcionOrdenadosPorFechaCreacionAsc(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        // Buscar trabajos independientes
        List<TrabajoIndependiente> trabajosIndependientes = trabajoIndependienteRepository
                .findByDescripcionContainingIgnoreCaseAndActivoTrue(descripcion);

        // Buscar trabajos de empresas
        List<TrabajoEmpresa> trabajosEmpresas = trabajoEmpresaRepository
                .findByDescripcionContainingIgnoreCaseAndActivoTrue(descripcion);

        // Convertir a TrabajoDTO y asignar tipoTrabajo
        List<TrabajoDTO> dtoIndependientes = trabajosIndependientes.stream()
                .map(t -> new TrabajoDTO(
                        t.getId(),
                        t.getDescripcion(),
                        t.getAverageRating(),
                        t.getActivo(),
                        t.getPrecio(),
                        t.getFechaCreacion(),
                        "Independiente"
                ))
                .collect(Collectors.toList());

        List<TrabajoDTO> dtoEmpresas = trabajosEmpresas.stream()
                .map(t -> new TrabajoDTO(
                        t.getId(),
                        t.getDescripcion(),
                        t.getAverageRating(),
                        t.getActivo(),
                        t.getPrecio(),
                        t.getFechaCreacion(),
                        "Empresa"
                ))
                .collect(Collectors.toList());

        // Combinar las listas
        List<TrabajoDTO> trabajosCombinados = dtoIndependientes;
        trabajosCombinados.addAll(dtoEmpresas);

        // Ordenar por fechaCreacion ascendente
        trabajosCombinados.sort((t1, t2) -> t1.getFechaCreacion().compareTo(t2.getFechaCreacion()));

        return trabajosCombinados;
    }



    @Override
    @Transactional(readOnly = true)
    public List<FiltrosConsultasIndependientesDTO> listarFiltrosConsultasIndependientesParametros(String pais, String ciudad, String descripcion, String filtro, String calificacion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        if (filtro.equals("asc")&& calificacion.equals("4")){
            return trabajoIndependienteRepository.findFiltrosByDescripcionPrecioAsc4(pais,ciudad,descripcion);
        }else if (filtro.equals("desc")&& calificacion.equals("4")){
            return trabajoIndependienteRepository.findFiltrosByDescripcionPrecioDesc4(pais,ciudad,descripcion);
        }else if (filtro.equals("asc")&& calificacion.equals("3")){
            return trabajoIndependienteRepository.findFiltrosByDescripcionPrecioAsc3(pais,ciudad,descripcion);
        }else if (filtro.equals("desc")&& calificacion.equals("3")){
            return trabajoIndependienteRepository.findFiltrosByDescripcionPrecioDesc3(pais,ciudad,descripcion);
        }else if (filtro.equals("ventas")&& calificacion.equals("4")){
            return trabajoIndependienteRepository.findFiltrosByVentas4(pais,ciudad,descripcion);
        }else if (filtro.equals("ventas")&& calificacion.equals("3")){
            return trabajoIndependienteRepository.findFiltrosByVentas3(pais,ciudad,descripcion);
        }

        return trabajoIndependienteRepository.findFiltrosByDescripcion(pais,ciudad,descripcion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FiltrosConsultasEmpresasDTO> listarFiltrosConsultasEmpresasParametros(String pais, String ciudad, String descripcion, String filtro, String calificacion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        if (filtro.equals("asc")&&calificacion.equals("4")){
            return trabajoEmpresaRepository.findFiltrosByDescripcionPrecioAsc4(pais,ciudad,descripcion);
        }else if(filtro.equals("desc")&&calificacion.equals("4")){
            return trabajoEmpresaRepository.findFiltrosByDescripcionPrecioDesc4(pais,ciudad,descripcion);
        }else if (filtro.equals("asc")&&calificacion.equals("3")){
            return trabajoEmpresaRepository.findFiltrosByDescripcionPrecioAsc3(pais,ciudad,descripcion);
        } else if (filtro.equals("desc")&&calificacion.equals("3")) {
            return trabajoEmpresaRepository.findFiltrosByDescripcionPrecioDesc3(pais,ciudad,descripcion);
        } else if (filtro.equals("ventas")&&calificacion.equals("4")) {
            return trabajoEmpresaRepository.findFiltrosByVentas4(pais,ciudad,descripcion);
        } else if (filtro.equals("ventas")&& calificacion.equals("3")) {
            return trabajoEmpresaRepository.findFiltrosByVentas3(pais,ciudad,descripcion);
        }


        return trabajoEmpresaRepository.findFiltrosByDescripcion(pais,ciudad,descripcion);
    }

    @Override
    @Transactional(readOnly = true)
    public FiltroTrabajoEmpresaDTO obtenerTrabajosEmpPorCliente(Long idTrabajoEmpresa){
        if (idTrabajoEmpresa == null){
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }

        return trabajoEmpresaRepository.findTrabajosEmpByCliente(idTrabajoEmpresa);
    }

    @Override
    @Transactional(readOnly = true)
    public FiltroTrabajoIndependienteDTO obtenerTrabajosIndPorCliente(Long idTrabajoIndependiente){
        if (idTrabajoIndependiente == null){
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }

        return trabajoEmpresaRepository.findTrabajosIndByCliente(idTrabajoIndependiente);
    }


}
