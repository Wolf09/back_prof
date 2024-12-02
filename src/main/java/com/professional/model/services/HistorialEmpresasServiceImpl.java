package com.professional.model.services;

import com.professional.model.dto.HistorialDTO;
import com.professional.model.entities.HistorialEmpresas;
import com.professional.model.entities.TrabajoEmpresa;
import com.professional.model.entities.Cliente;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.ClienteRepository;
import com.professional.model.repositories.HistorialEmpresasRepository;
import com.professional.model.repositories.TrabajoEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistorialEmpresasServiceImpl implements HistorialEmpresasService {

    private final HistorialEmpresasRepository historialEmpresasRepository;
    private final TrabajoEmpresaRepository trabajoEmpresaRepository;

    private final ClienteRepository clienteRepository;

    @Autowired
    public HistorialEmpresasServiceImpl(HistorialEmpresasRepository historialEmpresasRepository, TrabajoEmpresaRepository trabajoEmpresaRepository,
                                        ClienteRepository clienteRepository) {
        this.historialEmpresasRepository = historialEmpresasRepository;
        this.trabajoEmpresaRepository=trabajoEmpresaRepository;
        this.clienteRepository = clienteRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<HistorialEmpresas> getAllHistorialEmpresas() {
        return historialEmpresasRepository.findByActivoTrue();
    }

    @Override
    public List<HistorialEmpresas> getAllHistorialEmpresasIncludingInactive() {
        return historialEmpresasRepository.findAll();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public HistorialEmpresas getHistorialEmpresasById(Long id) {
        return historialEmpresasRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("HistorialEmpresas no encontrado con ID: " + id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public HistorialEmpresas createHistorialEmpresas(HistorialEmpresas historialEmpresas) {
        // Puedes agregar validaciones adicionales aquí si es necesario.
        // Verificar que el Cliente exista
        Cliente cliente = clienteRepository.findById(historialEmpresas.getCliente().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + historialEmpresas.getCliente().getId()));
        TrabajoEmpresa trabajoEmpresa= trabajoEmpresaRepository.findById(historialEmpresas.getTrabajo().getId())
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoIndependiente no encontrado con ID: " + historialEmpresas.getTrabajo().getId()));

        historialEmpresas.setCliente(cliente);
        historialEmpresas.setTrabajo(trabajoEmpresa);
        return historialEmpresasRepository.save(historialEmpresas);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public HistorialEmpresas updateHistorialEmpresas(Long id, HistorialEmpresas historialEmpresasDetalles) {
        HistorialEmpresas existente = historialEmpresasRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("HistorialEmpresas no encontrado con ID o esta inactivo: " + id));

        // Actualizar campos según sea necesario.
        existente.setComentarios(historialEmpresasDetalles.getComentarios());
        // Actualizar relaciones si es necesario
        if (historialEmpresasDetalles.getCliente() != null) {
            Cliente cliente = clienteRepository.findById(historialEmpresasDetalles.getCliente().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + historialEmpresasDetalles.getCliente().getId()));
            existente.setCliente(cliente);
        }

        if (historialEmpresasDetalles.getTrabajo() != null) {
            TrabajoEmpresa trabajo = trabajoEmpresaRepository.findById(historialEmpresasDetalles.getTrabajo().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("TrabajoEmpresa no encontrado con ID: " + historialEmpresasDetalles.getTrabajo().getId()));
            existente.setTrabajo(trabajo);
        }


        // Manejar relaciones si es necesario.
        // Por ejemplo, si necesitas actualizar listas relacionadas, puedes hacerlo aquí.

        return historialEmpresasRepository.save(existente);
    }

    /**
     * {@inheritDoc}
     */
    public void deleteHistorialEmpresas(Long id) {
        HistorialEmpresas existente = historialEmpresasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HistorialEmpresas no encontrado con ID: " + id));

        if (!existente.getActivo()) {
            throw new IllegalStateException("El HistorialEmpresas ya está inactivo.");
        }

        // Eliminación lógica: establecer 'activo' a false
        existente.setActivo(false);
        historialEmpresasRepository.save(existente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<HistorialEmpresas> getHistorialEmpresasByTrabajoEmpresa(TrabajoEmpresa trabajoEmpresa) {
        return historialEmpresasRepository.findByTrabajo(trabajoEmpresa);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<HistorialEmpresas> getHistorialEmpresasByCliente(Cliente cliente) {
        return historialEmpresasRepository.findByCliente(cliente);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<HistorialEmpresas> findByClienteAndTrabajo(Long clienteId, Long trabajoId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + clienteId));

        TrabajoEmpresa trabajoEmpresa = trabajoEmpresaRepository.findById(trabajoId)
                .orElseThrow(() -> new ResourceNotFoundException("TrabajoEmpresa no encontrado con ID: " + trabajoId));

        return historialEmpresasRepository.findByClienteAndTrabajoAndActivoTrue(cliente, trabajoEmpresa);
    }

}

