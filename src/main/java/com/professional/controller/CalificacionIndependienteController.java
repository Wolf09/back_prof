package com.professional.controller;

import com.professional.model.dto.Error;
import com.professional.model.entities.CalificacionIndependientes;
import com.professional.model.entities.Cliente;
import com.professional.model.entities.TrabajoIndEnAccion;
import com.professional.model.entities.TrabajoIndependiente;
import com.professional.model.services.CalificacionIndependientesService;
import com.professional.model.services.ClienteService;
import com.professional.model.services.TrabajoIndEnAccionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar las operaciones CRUD de la entidad CalificacionIndependientes.
 */
@RestController
@RequestMapping("/calificaciones")
public class CalificacionIndependienteController {

    private final CalificacionIndependientesService calificacionService;
    private final ClienteService clienteService;
    private final TrabajoIndEnAccionService trabajoIndEnAccionService;

    @Autowired
    public CalificacionIndependienteController(CalificacionIndependientesService calificacionService,
                                               ClienteService clienteService,
                                               TrabajoIndEnAccionService trabajoIndEnAccionService) {
        this.calificacionService = calificacionService;
        this.clienteService = clienteService;
        this.trabajoIndEnAccionService = trabajoIndEnAccionService;
    }

    /**
     * Crear una nueva CalificacionIndependientes.
     *
     * @param calificacion Datos de la CalificacionIndependientes a crear.
     * @param result       Resultado de la validación.
     * @return ResponseEntity con la CalificacionIndependientes creada o errores de validación.
     */
    @Transactional
    @PostMapping
    public ResponseEntity<?> crearCalificacion(@Valid @RequestBody CalificacionIndependientes calificacion, BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        CalificacionIndependientes creada = calificacionService.createCalificacion(calificacion);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    /**
     * Listar todas las CalificacionesIndependientes.
     *
     * @return ResponseEntity con la lista de CalificacionesIndependientes.
     */
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<CalificacionIndependientes>> listarCalificaciones() {
        List<CalificacionIndependientes> calificaciones = calificacionService.getAllCalificaciones();
        return new ResponseEntity<>(calificaciones, HttpStatus.OK);
    }

    /**
     * Obtener una CalificacionIndependientes por su ID.
     *
     * @param id ID de la CalificacionIndependientes.
     * @return ResponseEntity con la CalificacionIndependientes encontrada.
     */
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<CalificacionIndependientes> obtenerCalificacionPorId(@PathVariable Long id) {
        CalificacionIndependientes calificacion = calificacionService.getCalificacionById(id);
        return new ResponseEntity<>(calificacion, HttpStatus.OK);
    }

    /**
     * Actualizar una CalificacionIndependientes existente.
     *
     * @param id                  ID de la CalificacionIndependientes a actualizar.
     * @param calificacionDetalles Datos actualizados de la CalificacionIndependientes.
     * @param result              Resultado de la validación.
     * @return ResponseEntity con la CalificacionIndependientes actualizada o errores de validación.
     */
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCalificacion(@PathVariable Long id,
                                                    @Valid @RequestBody CalificacionIndependientes calificacionDetalles,
                                                    BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        CalificacionIndependientes actualizado = calificacionService.updateCalificacion(id, calificacionDetalles);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    /**
     * Eliminar una CalificacionIndependientes por su ID (eliminación física en la base de datos).
     *
     * @param id ID de la CalificacionIndependientes a eliminar.
     * @return ResponseEntity con el estado de la operación.
     */
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCalificacion(@PathVariable Long id) {
        calificacionService.deleteCalificacion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Buscar una CalificacionIndependientes por Cliente y TrabajoIndependiente.
     *
     * @param clienteId  ID del Cliente que realizó la calificación.
     * @param trabajoId  ID del TrabajoIndependiente calificado.
     * @return ResponseEntity con la CalificacionIndependientes encontrada.
     */
    @Transactional(readOnly = true)
    @GetMapping("/buscar")
    public ResponseEntity<CalificacionIndependientes> buscarCalificacionPorClienteYTrabajo(
            @RequestParam Long clienteId,
            @RequestParam Long trabajoId) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        TrabajoIndEnAccion trabajoIndEnAccion = trabajoIndEnAccionService.getTrabajoEnAccionById(trabajoId);

        CalificacionIndependientes calificacion = calificacionService.getCalificacionByClienteAndTrabajo(cliente, trabajoIndEnAccion);
        return new ResponseEntity<>(calificacion, HttpStatus.OK);
    }

    /**
     * Verificar si existe una CalificacionIndependientes para un Cliente y TrabajoIndEnAccion específicos.
     *
     * @param clienteId              ID del Cliente que realizó la calificación.
     * @param trabajoIndEnAccionId   ID del TrabajoIndEnAccion calificado.
     * @return ResponseEntity con un indicador booleano.
     */
    @Transactional(readOnly = true)
    @GetMapping("/existe")
    public ResponseEntity<Boolean> existeCalificacionPorClienteYTrabajo(
            @RequestParam Long clienteId,
            @RequestParam Long trabajoIndEnAccionId) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        TrabajoIndEnAccion trabajoIndEnAccion = trabajoIndEnAccionService.getTrabajoEnAccionById(trabajoIndEnAccionId);

        boolean existe = calificacionService.existsCalificacionByClienteAndTrabajo(cliente, trabajoIndEnAccion);
        return new ResponseEntity<>(existe, HttpStatus.OK);
    }
}

