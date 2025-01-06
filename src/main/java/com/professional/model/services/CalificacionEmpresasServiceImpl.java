package com.professional.model.services;

import com.professional.model.entities.CalificacionEmpresas;
import com.professional.model.enums.EstadoTrabajo;
import com.professional.model.entities.TrabajoEmpresa;
import com.professional.model.entities.Cliente;
import com.professional.model.exceptions.ResourceNotFoundException;
import com.professional.model.repositories.CalificacionEmpresasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CalificacionEmpresasServiceImpl implements CalificacionEmpresasService {

    private final CalificacionEmpresasRepository calificacionRepository;
    private final TrabajoEmpresaService trabajoEmpresaService;
    private final ClienteService clienteService;

    @Autowired
    public CalificacionEmpresasServiceImpl(CalificacionEmpresasRepository calificacionRepository,
                                           TrabajoEmpresaService trabajoEmpresaService,
                                           ClienteService clienteService) {
        this.calificacionRepository = calificacionRepository;
        this.trabajoEmpresaService = trabajoEmpresaService;
        this.clienteService = clienteService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalificacionEmpresas> getAllCalificaciones() {
        return calificacionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CalificacionEmpresas getCalificacionById(Long id) {
        return calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalificacionEmpresas no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public CalificacionEmpresas createCalificacion(CalificacionEmpresas calificacion) {
        // Validaciones adicionales
        // Verificar que el Cliente y TrabajoEmpresa existan
        Cliente cliente = clienteService.getClienteById(calificacion.getCliente().getId());
        TrabajoEmpresa trabajo = trabajoEmpresaService.getTrabajoEmpresaById(calificacion.getTrabajo().getId());

        // Verificar si ya existe una calificación de este cliente para este trabajo
        if (calificacionRepository.existsByClienteAndTrabajo(cliente, trabajo)) {
            throw new IllegalStateException("El cliente ya ha calificado este trabajo empresa");
        }
        // Verificar que el Cliente es el creador del TrabajoEmpresa
        if (!trabajo.getCliente().getId().equals(cliente.getId())) {
            throw new ResourceNotFoundException("El cliente no es el creador de este TrabajoEmpresa");
        }

        boolean finalizado = trabajo.getTrabajoEmpEnAccions().stream()
                .anyMatch(teea -> teea.getEstadoTrabajo() == EstadoTrabajo.FINALIZADO);
        if (!finalizado) {
            throw new ResourceNotFoundException("No se puede calificar un TrabajoEmpresa que no ha sido finalizado");
        }
        calificacion.setCliente(cliente);
        calificacion.setTrabajo(trabajo);

        CalificacionEmpresas creado = calificacionRepository.save(calificacion);

        // Actualizar el promedio de calificación en TrabajoEmpresa
        actualizarPromedioCalificacion(trabajo);

        return creado;
    }

    @Override
    @Transactional
    public CalificacionEmpresas updateCalificacion(Long id, CalificacionEmpresas calificacionDetalles) {
        CalificacionEmpresas existente = calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalificacionEmpresas no encontrada con ID: " + id));

        // Actualizar campos permitidos
        existente.setRating(calificacionDetalles.getRating());
        existente.setComentarios(calificacionDetalles.getComentarios());

        // Asumimos que el Cliente y TrabajoEmpresa no se pueden cambiar en una calificación existente
        if (calificacionDetalles.getRating() != null) {
            existente.setRating(calificacionDetalles.getRating());
        }
        if (calificacionDetalles.getComentarios() != null) {
            existente.setComentarios(calificacionDetalles.getComentarios());
        }

        CalificacionEmpresas actualizado = calificacionRepository.save(existente);

        // Actualizar el promedio de calificación en TrabajoEmpresa
        actualizarPromedioCalificacion(actualizado.getTrabajo());

        return actualizado;
    }

    @Override
    @Transactional
    public void deleteCalificacion(Long id) {
        CalificacionEmpresas existente = calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalificacionEmpresas no encontrada con ID: " + id));
        calificacionRepository.delete(existente);

        // Actualizar el promedio de calificación en TrabajoEmpresa
        actualizarPromedioCalificacion(existente.getTrabajo());
    }

    @Override
    @Transactional(readOnly = true)
    public CalificacionEmpresas getCalificacionByClienteAndTrabajo(Cliente cliente, TrabajoEmpresa trabajoEmpresa) {
        return calificacionRepository.findByClienteAndTrabajo(cliente, trabajoEmpresa)
                .orElseThrow(() -> new ResourceNotFoundException("CalificacionEmpresas no encontrada para el Cliente ID: "
                        + cliente.getId() + " y TrabajoEmpresa ID: " + trabajoEmpresa.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsCalificacionByClienteAndTrabajo(Cliente cliente, TrabajoEmpresa trabajoEmpresa) {
        return calificacionRepository.existsByClienteAndTrabajo(cliente, trabajoEmpresa);
    }

    /**
     * Método para actualizar el promedio de calificaciones de un TrabajoEmpresa
     *
     * @param trabajoEmpresa El TrabajoEmpresa cuyo promedio se actualizará
     */
    private void actualizarPromedioCalificacion(TrabajoEmpresa trabajoEmpresa) {
        Double promedio = calificacionRepository.findAverageRatingByTrabajo(trabajoEmpresa);
        if (promedio == null) {
            promedio = 0.0; // Puedes establecer un valor por defecto si no hay calificaciones
        }
        trabajoEmpresa.setAverageRating(promedio);
        trabajoEmpresaService.saveTrabajoEmpresa(trabajoEmpresa);
    }


}

