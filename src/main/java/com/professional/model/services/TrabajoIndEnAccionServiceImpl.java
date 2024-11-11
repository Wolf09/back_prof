package com.professional.model.services;

import com.professional.model.entities.*;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.TrabajoIndEnAccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrabajoIndEnAccionServiceImpl implements TrabajoIndEnAccionService {

    private final TrabajoIndEnAccionRepository trabajoIndEnAccionRepository;
    private final HistorialIndependientesService historialService;
    private final TrabajoIndependienteService trabajoIndependienteService;

    @Autowired
    public TrabajoIndEnAccionServiceImpl(TrabajoIndEnAccionRepository trabajoIndEnAccionRepository,
                                         HistorialIndependientesService historialService,
                                         TrabajoIndependienteService trabajoIndependienteService) {
        this.trabajoIndEnAccionRepository = trabajoIndEnAccionRepository;
        this.historialService = historialService;
        this.trabajoIndependienteService = trabajoIndependienteService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoIndEnAccion> getAllTrabajosEnAccion() {
        return trabajoIndEnAccionRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public TrabajoIndEnAccion getTrabajoEnAccionById(Long id) {
        return trabajoIndEnAccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoIndEnAccion no encontrado con ID: " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TrabajoIndEnAccion createTrabajoEnAccion(TrabajoIndEnAccion trabajoEnAccion) {
        // Establecer estadoTrabajo en PENDIENTE automáticamente
        trabajoEnAccion.setEstadoTrabajo(EstadoTrabajo.PENDIENTE);
        trabajoEnAccion.setActivo(true); // Establecer activo en true al crear
        return trabajoIndEnAccionRepository.save(trabajoEnAccion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public TrabajoIndEnAccion updateEstadoTrabajo(Long id, EstadoTrabajo estadoTrabajo) {
        TrabajoIndEnAccion trabajoIndEnAccion = trabajoIndEnAccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoIndEnAccion no encontrado con ID: " + id));

        EstadoTrabajo oldEstado = trabajoIndEnAccion.getEstadoTrabajo();
        trabajoIndEnAccion.setEstadoTrabajo(estadoTrabajo);
        trabajoIndEnAccion.setFechaCambio(LocalDateTime.now());
        TrabajoIndEnAccion actualizado = trabajoIndEnAccionRepository.save(trabajoIndEnAccion);

        // Verificar si el estado se ha cambiado a FINALIZADO
        if (estadoTrabajo == EstadoTrabajo.FINALIZADO && oldEstado != EstadoTrabajo.FINALIZADO) {
            // Crear un nuevo HistorialIndependientes
            TrabajoIndependiente trabajoIndependiente = trabajoIndEnAccion.getTrabajoIndependiente();
            Cliente cliente = trabajoIndependiente.getCliente(); // Asegúrate de que TrabajoIndependiente tiene un campo Cliente

            HistorialIndependientes historial = new HistorialIndependientes();
            historial.setCliente(cliente);
            historial.setTrabajo(trabajoIndependiente);
            historial.setComentarios("Trabajo finalizado exitosamente"); // Puedes ajustar los comentarios según necesidad

            historialService.createHistorialIndependientes(historial);
        }

        return actualizado;
    }

    /**
     * {@inheritDoc}
     *
     * Solo usuarios con rol INDEPENDIENTE pueden cambiar el estadoTrabajo.
     */
    @Override
    @Transactional
    @PreAuthorize("hasRole('INDEPENDIENTE')")
    public TrabajoIndEnAccion updateTrabajoEnAccion(Long id, TrabajoIndEnAccion trabajoEnAccionDetalles) {
        TrabajoIndEnAccion existente = trabajoIndEnAccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoIndEnAccion no encontrado con ID: " + id));

        // Actualizar campos permitidos
        existente.setTrabajoIndependiente(trabajoEnAccionDetalles.getTrabajoIndependiente());
        existente.setEstadoTrabajo(trabajoEnAccionDetalles.getEstadoTrabajo());

        // Nota: fechaCambio es updatable = false en la entidad, así que no se actualiza.

        return trabajoIndEnAccionRepository.save(existente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteTrabajoEnAccion(Long id) {
        TrabajoIndEnAccion existente = trabajoIndEnAccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoIndEnAccion no encontrado con ID: " + id));
        // Eliminación lógica: establecer 'activo' a false
        existente.setActivo(false);
        trabajoIndEnAccionRepository.save(existente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoIndEnAccion> getTrabajosEnAccionByTrabajoIndependiente(TrabajoIndependiente trabajoIndependiente) {
        return trabajoIndEnAccionRepository.findByTrabajoIndependiente(trabajoIndependiente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoIndEnAccion> getAllTrabajosEnAccionActivos() {
        return trabajoIndEnAccionRepository.findByActivo(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoIndEnAccion> getAllTrabajosEnAccionInactivos() {
        return trabajoIndEnAccionRepository.findByActivo(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoIndEnAccion> findByEstadoTrabajo(EstadoTrabajo estadoTrabajo) {
        return trabajoIndEnAccionRepository.findByEstadoTrabajo(estadoTrabajo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrabajoIndEnAccion> getTrabajosEnAccionByCliente(Cliente cliente) {
        return trabajoIndEnAccionRepository.findByTrabajoIndependiente_Cliente(cliente);
    }
}
