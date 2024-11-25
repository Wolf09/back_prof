package com.professional.model.services;

import com.professional.controller.exceptions.InvalidOperationException;
import com.professional.model.dto.HistorialDTO;
import com.professional.model.dto.TrabajoEnAccionDTO;
import com.professional.model.entities.*;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.TrabajoEmpEnAccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrabajoEmpEnAccionServiceImpl implements TrabajoEmpEnAccionService {

    private final TrabajoEmpEnAccionRepository trabajoEmpEnAccionRepository;
    private final HistorialEmpresasService historialService;
    private final TrabajoEmpresaService trabajoEmpresaService;
    private final ClienteService clienteService;

    @Autowired
    public TrabajoEmpEnAccionServiceImpl(TrabajoEmpEnAccionRepository trabajoEmpEnAccionRepository,
                                         HistorialEmpresasService historialService,
                                         TrabajoEmpresaService trabajoEmpresaService,
                                         ClienteService clienteService) {
        this.trabajoEmpEnAccionRepository = trabajoEmpEnAccionRepository;
        this.historialService = historialService;
        this.trabajoEmpresaService = trabajoEmpresaService;
        this.clienteService = clienteService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoEmpEnAccion> getAllTrabajosEmpEnAccion() {
        return trabajoEmpEnAccionRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public TrabajoEmpEnAccion getTrabajoEmpEnAccionById(Long id) {
        return trabajoEmpEnAccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoEmpEnAccion no encontrado con ID: " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TrabajoEmpEnAccion createTrabajoEmpEnAccion(TrabajoEmpEnAccion trabajoEmpEnAccion) {
        TrabajoEmpresa trabajoEmpresa = trabajoEmpresaService.getTrabajoEmpresaById(trabajoEmpEnAccion.getTrabajoEmpresa().getId());

        trabajoEmpEnAccion.setTrabajoEmpresa(trabajoEmpresa);
        trabajoEmpEnAccion.setEstadoTrabajo(EstadoTrabajo.PENDIENTE); // Estado inicial
        trabajoEmpEnAccion.setActivo(true); // Asegurar que el registro sea activo al crear
        return trabajoEmpEnAccionRepository.save(trabajoEmpEnAccion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TrabajoEmpEnAccion updateTrabajoEmpEnAccion(Long id, TrabajoEmpEnAccion trabajoEmpEnAccionDetalles) {
        TrabajoEmpEnAccion existente = trabajoEmpEnAccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoEmpEnAccion no encontrado con ID: " + id));

        existente.setEstadoTrabajo(trabajoEmpEnAccionDetalles.getEstadoTrabajo());
        if (existente.getEstadoTrabajo() == EstadoTrabajo.FINALIZADO) {
            existente.setEstadoTrabajo(EstadoTrabajo.FINALIZADO);
        }

        // Validaciones adicionales según reglas de negocio

        // Si necesitas actualizar relaciones, puedes hacerlo aquí.
        existente.setTrabajoEmpresa(trabajoEmpEnAccionDetalles.getTrabajoEmpresa());

        return trabajoEmpEnAccionRepository.save(existente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteTrabajoEmpEnAccion(Long id) {
        TrabajoEmpEnAccion existente = trabajoEmpEnAccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoEmpEnAccion no encontrado con ID: " + id));
        // Eliminación lógica: establecer 'activo' a false
        existente.setActivo(false);
        trabajoEmpEnAccionRepository.save(existente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoEmpEnAccion> getTrabajosEmpEnAccionByTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa) {
        return trabajoEmpEnAccionRepository.findByTrabajoEmpresa(trabajoEmpresa);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TrabajoEnAccionDTO updateEstadoTrabajo(Long id, EstadoTrabajo estadoTrabajo) {
        TrabajoEmpEnAccion trabajoEmpEnAccion = trabajoEmpEnAccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoEmpEnAccion no encontrado con ID: " + id));

        EstadoTrabajo oldEstado = trabajoEmpEnAccion.getEstadoTrabajo();
        trabajoEmpEnAccion.setEstadoTrabajo(estadoTrabajo);
        trabajoEmpEnAccion.setFechaCambio(LocalDateTime.now());
        TrabajoEmpEnAccion actualizado = trabajoEmpEnAccionRepository.save(trabajoEmpEnAccion);

        HistorialDTO historialDTO = null;
        // Verificar si el estado se ha cambiado a FINALIZADO
        if (estadoTrabajo == EstadoTrabajo.FINALIZADO && oldEstado != EstadoTrabajo.FINALIZADO) {
            // Crear un nuevo HistorialEmpresas
            TrabajoEmpresa trabajoEmpresa = trabajoEmpEnAccion.getTrabajoEmpresa();
            Cliente cliente = trabajoEmpresa.getCliente(); // Asume que TrabajoEmpresa tiene un campo Cliente

            HistorialEmpresas historial = new HistorialEmpresas();
            historial.setCliente(cliente);
            historial.setTrabajo(trabajoEmpresa);
            historial.setFechaSolicitud(LocalDateTime.now());
            historial.setActivo(true);

            historialDTO=historialService.createHistorialEmpresasDTO(historial);
        }
        // Mapear la entidad actualizada a DTO
        TrabajoEnAccionDTO trabajoDTO = new TrabajoEnAccionDTO();
        trabajoDTO.setId(actualizado.getId());
        trabajoDTO.setEstadoTrabajo(actualizado.getEstadoTrabajo());
        trabajoDTO.setFechaCambio(actualizado.getFechaCambio());
        trabajoDTO.setActivo(actualizado.getActivo());

        if (historialDTO != null) {
            trabajoDTO.setHistorial(historialDTO);
        }

        return trabajoDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoEmpEnAccion> findByActivo(Boolean activo) {
        return trabajoEmpEnAccionRepository.findByActivo(activo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoEmpEnAccion> findByEstadoTrabajo(EstadoTrabajo estadoTrabajo) {
        return trabajoEmpEnAccionRepository.findByEstadoTrabajo(estadoTrabajo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoEmpEnAccion> getTrabajosEmpEnAccionByCliente(Cliente cliente) {
        return trabajoEmpEnAccionRepository.findByTrabajoEmpresa_Cliente(cliente);
    }
}
