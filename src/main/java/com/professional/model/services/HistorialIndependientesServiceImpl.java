package com.professional.model.services;

import com.professional.model.entities.CalificacionIndependientes;
import com.professional.model.entities.HistorialIndependientes;
import com.professional.model.entities.TrabajoIndependiente;
import com.professional.model.entities.Cliente;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.HistorialIndependientesRepository;
import com.professional.model.repositories.TrabajoIndependienteRepository;
import com.professional.model.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistorialIndependientesServiceImpl implements HistorialIndependientesService {

    private final HistorialIndependientesRepository historialIndependientesRepository;
    private final ClienteRepository clienteRepository;
    private final TrabajoIndependienteRepository trabajoIndependienteRepository;

    @Autowired
    public HistorialIndependientesServiceImpl(
            HistorialIndependientesRepository historialIndependientesRepository,
            ClienteRepository clienteRepository,
            TrabajoIndependienteRepository trabajoIndependienteRepository) {
        this.historialIndependientesRepository = historialIndependientesRepository;
        this.clienteRepository = clienteRepository;
        this.trabajoIndependienteRepository = trabajoIndependienteRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<HistorialIndependientes> getAllHistorialIndependientes() {
        return historialIndependientesRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public HistorialIndependientes getHistorialIndependientesById(Long id) {
        return historialIndependientesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HistorialIndependientes no encontrado con ID: " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public HistorialIndependientes createHistorialIndependientes(HistorialIndependientes historialIndependientes) {
        // Verificar que el Cliente exista
        Cliente cliente = clienteRepository.findById(historialIndependientes.getCliente().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + historialIndependientes.getCliente().getId()));

        // Verificar que el TrabajoIndependiente exista
        TrabajoIndependiente trabajo = trabajoIndependienteRepository.findById(historialIndependientes.getTrabajo().getId())
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoIndependiente no encontrado con ID: " + historialIndependientes.getTrabajo().getId()));

        // Asignar las entidades verificadas
        historialIndependientes.setCliente(cliente);
        historialIndependientes.setTrabajo(trabajo);

        return historialIndependientesRepository.save(historialIndependientes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public HistorialIndependientes updateHistorialIndependientes(Long id, HistorialIndependientes historialIndependientesDetalles) {
        HistorialIndependientes existente = historialIndependientesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HistorialIndependientes no encontrado con ID: " + id));

        // Actualizar campos básicos
        existente.setComentarios(historialIndependientesDetalles.getComentarios());

        // Actualizar relaciones si es necesario
        if (historialIndependientesDetalles.getCliente() != null) {
            Cliente cliente = clienteRepository.findById(historialIndependientesDetalles.getCliente().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + historialIndependientesDetalles.getCliente().getId()));
            existente.setCliente(cliente);
        }

        if (historialIndependientesDetalles.getTrabajo() != null) {
            TrabajoIndependiente trabajo = trabajoIndependienteRepository.findById(historialIndependientesDetalles.getTrabajo().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("TrabajoIndependiente no encontrado con ID: " + historialIndependientesDetalles.getTrabajo().getId()));
            existente.setTrabajo(trabajo);
        }

        return historialIndependientesRepository.save(existente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteHistorialIndependientes(Long id) {
        HistorialIndependientes existente = historialIndependientesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HistorialIndependientes no encontrado con ID: " + id));
        historialIndependientesRepository.delete(existente);
    }
}

