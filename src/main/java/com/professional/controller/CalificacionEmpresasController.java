package com.professional.controller;

import com.professional.model.entities.CalificacionEmpresas;
import com.professional.model.entities.Cliente;
import com.professional.model.entities.TrabajoEmpresa;
import com.professional.model.dto.Error;
import com.professional.model.services.CalificacionEmpresasService;
import com.professional.model.services.ClienteService;
import com.professional.model.services.TrabajoEmpresaService;
import com.professional.model.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/calificaciones-empresas")
public class CalificacionEmpresasController {

    private final CalificacionEmpresasService calificacionService;
    private final ClienteService clienteService;
    private final TrabajoEmpresaService trabajoEmpresaService;

    @Autowired
    public CalificacionEmpresasController(CalificacionEmpresasService calificacionService,
                                          ClienteService clienteService,
                                          TrabajoEmpresaService trabajoEmpresaService) {
        this.calificacionService = calificacionService;
        this.clienteService = clienteService;
        this.trabajoEmpresaService = trabajoEmpresaService;
    }

    /**
     * Crear una nueva CalificacionEmpresas.
     *
     * @param calificacion Objeto CalificacionEmpresas a crear.
     * @param result       Resultado de la validación.
     * @return ResponseEntity con la CalificacionEmpresas creada o lista de errores.
     */
    @Transactional
    @PostMapping
    public ResponseEntity<?> crearCalificacion(@Valid @RequestBody CalificacionEmpresas calificacion, BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        CalificacionEmpresas creada = calificacionService.createCalificacion(calificacion);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    /**
     * Listar todas las CalificacionesEmpresas.
     *
     * @return ResponseEntity con la lista de CalificacionesEmpresas.
     */
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<CalificacionEmpresas>> listarCalificaciones() {
        List<CalificacionEmpresas> calificaciones = calificacionService.getAllCalificaciones();
        return new ResponseEntity<>(calificaciones, HttpStatus.OK);
    }

    /**
     * Obtener una calificación por su ID.
     *
     * @param id ID de la calificación.
     * @return CalificacionEmpresas encontrada.
     */
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<CalificacionEmpresas> getCalificacionById(@PathVariable Long id) {
        CalificacionEmpresas calificacion = calificacionService.getCalificacionById(id);
        return new ResponseEntity<>(calificacion, HttpStatus.OK);
    }

    /**
     * Actualizar una calificación existente.
     *
     * @param id                ID de la calificación a actualizar.
     * @param calificacionDetalles Datos actualizados de la calificación.
     * @param result            Resultado de la validación.
     * @return CalificacionEmpresas actualizada o lista de errores.
     */
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCalificacion(@PathVariable Long id,
                                                @Valid @RequestBody CalificacionEmpresas calificacionDetalles,
                                                BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        CalificacionEmpresas actualizado = calificacionService.updateCalificacion(id, calificacionDetalles);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    /**
     * Eliminar una calificación por su ID.
     *
     * @param id ID de la calificación a eliminar.
     * @return Respuesta sin contenido.
     */
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalificacion(@PathVariable Long id) {
        calificacionService.deleteCalificacion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Obtener una calificación específica por Cliente y TrabajoEmpresa.
     *
     * @param clienteId ID del Cliente.
     * @param trabajoId ID del TrabajoEmpresa.
     * @return CalificacionEmpresas encontrada.
     */
    @Transactional(readOnly = true)
    @GetMapping("/cliente/{clienteId}/trabajo/{trabajoId}")
    public ResponseEntity<CalificacionEmpresas> getCalificacionByClienteAndTrabajo(@PathVariable Long clienteId,
                                                                                   @PathVariable Long trabajoId) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        TrabajoEmpresa trabajo = trabajoEmpresaService.getTrabajoEmpresaById(trabajoId);
        CalificacionEmpresas calificacion = calificacionService.getCalificacionByClienteAndTrabajo(cliente, trabajo);
        return new ResponseEntity<>(calificacion, HttpStatus.OK);
    }

    /**
     * Verificar si existe una calificación para un Cliente y TrabajoEmpresa específicos.
     *
     * @param clienteId ID del Cliente.
     * @param trabajoId ID del TrabajoEmpresa.
     * @return true si existe, false de lo contrario.
     */
    @Transactional(readOnly = true)
    @GetMapping("/existencia/cliente/{clienteId}/trabajo/{trabajoId}")
    public ResponseEntity<Boolean> existsCalificacionByClienteAndTrabajo(@PathVariable Long clienteId,
                                                                         @PathVariable Long trabajoId) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        TrabajoEmpresa trabajo = trabajoEmpresaService.getTrabajoEmpresaById(trabajoId);
        boolean existe = calificacionService.existsCalificacionByClienteAndTrabajo(cliente, trabajo);
        return new ResponseEntity<>(existe, HttpStatus.OK);
    }
}
