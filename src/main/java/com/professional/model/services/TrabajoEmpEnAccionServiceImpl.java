package com.professional.model.services;

import com.professional.model.dto.HistorialDTO;
import com.professional.model.dto.TrabajoEnAccionDTO;
import com.professional.model.entities.*;
import com.professional.model.enums.EstadoTrabajo;
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
        HistorialEmpresas historialEmpresas=null;
        TrabajoEmpEnAccion existente = trabajoEmpEnAccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoEmpEnAccion no encontrado con ID: " + id));

        historialEmpresas=this.updateEstadoTrabajo(id,trabajoEmpEnAccionDetalles.getEstadoTrabajo());
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
    public HistorialEmpresas updateEstadoTrabajo(Long id, EstadoTrabajo estadoTrabajo) {
        TrabajoEmpEnAccion trabajoEmpEnAccion=trabajoEmpEnAccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoEmpEnAccion no encontrado con ID: " + id));

        EstadoTrabajo oldEstado= trabajoEmpEnAccion.getEstadoTrabajo();
        trabajoEmpEnAccion.setEstadoTrabajo(estadoTrabajo);
        trabajoEmpEnAccion.setFechaCambio(LocalDateTime.now());
        TrabajoEmpEnAccion actualizado= trabajoEmpEnAccionRepository.save(trabajoEmpEnAccion);
        HistorialEmpresas historialEmpresas=null;
        if (estadoTrabajo == EstadoTrabajo.FINALIZADO && oldEstado != EstadoTrabajo.FINALIZADO){

            TrabajoEmpresa trabajoEmpresa= trabajoEmpEnAccion.getTrabajoEmpresa();
            trabajoEmpresa.setVentas(trabajoEmpresa.getVentas()+1L);
            Cliente cliente= trabajoEmpEnAccion.getCliente();

            HistorialEmpresas historial= new HistorialEmpresas();
            historial.setCliente(cliente);
            historial.setTrabajo(trabajoEmpresa);
            historial.setFechaSolicitud(LocalDateTime.now());
            historial.setActivo(true);

            historialEmpresas= historialService.createHistorialEmpresas(historial);

        }
        return historialEmpresas;
    }

    @Override
    public void updateEstadoTrabajoEnAccion(Long id, EstadoTrabajo estadoTrabajo){
        TrabajoEmpEnAccion trabajoEmpEnAccion=trabajoEmpEnAccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoEmpEnAccion no encontrado con ID: " + id));
        trabajoEmpEnAccion.setEstadoTrabajo(estadoTrabajo);
        trabajoEmpEnAccionRepository.save(trabajoEmpEnAccion);

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
