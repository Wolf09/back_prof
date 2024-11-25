package com.professional.controller;

import com.professional.model.entities.HistorialIndependientes;
import com.professional.model.dto.Error;
import com.professional.model.services.HistorialIndependientesService;
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
@RequestMapping("/historial-independientes")
public class HistorialIndependientesController {

    private final HistorialIndependientesService historialService;

    @Autowired
    public HistorialIndependientesController(HistorialIndependientesService historialService) {
        this.historialService = historialService;
    }

    /**
     * Crear un nuevo HistorialIndependientes.
     *
     * @param historial Objeto HistorialIndependientes a crear.
     * @param result    Resultado de la validación.
     * @return ResponseEntity con el HistorialIndependientes creado o lista de errores.
     */
    @Transactional
    @PostMapping
    public ResponseEntity<?> crearHistorialIndependientes(@Valid @RequestBody HistorialIndependientes historial, BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        HistorialIndependientes creado = historialService.createHistorialIndependientes(historial);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    /**
     * Listar todos los HistorialIndependientes activos.
     *
     * @return ResponseEntity con la lista de HistorialIndependientes activos.
     */
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<HistorialIndependientes>> listarHistorialIndependientes() {
        List<HistorialIndependientes> historial = historialService.getAllHistorialIndependientes();
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }

    /**
     * Obtener un HistorialIndependientes por su ID (solo si está activo).
     *
     * @param id ID del HistorialIndependientes.
     * @return HistorialIndependientes encontrado.
     */
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<HistorialIndependientes> getHistorialIndependientesById(@PathVariable Long id) {
        HistorialIndependientes historial = historialService.getHistorialIndependientesById(id);
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }

    /**
     * Actualizar un HistorialIndependientes existente.
     *
     * @param id                      ID del HistorialIndependientes a actualizar.
     * @param historialDetalles Datos actualizados del HistorialIndependientes.
     * @param result                  Resultado de la validación.
     * @return HistorialIndependientes actualizado o lista de errores.
     */
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateHistorialIndependientes(@PathVariable Long id,
                                                           @Valid @RequestBody HistorialIndependientes historialDetalles,
                                                           BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        HistorialIndependientes actualizado = historialService.updateHistorialIndependientes(id, historialDetalles);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    /**
     * Eliminar (lógicamente) un HistorialIndependientes por su ID.
     *
     * @param id ID del HistorialIndependientes a eliminar.
     * @return Respuesta sin contenido.
     */
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorialIndependientes(@PathVariable Long id) {
        historialService.deleteHistorialIndependientes(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Buscar HistorialIndependientes por Cliente y TrabajoIndependiente activos.
     *
     * @param clienteId ID del Cliente.
     * @param trabajoId ID del TrabajoIndependiente.
     * @return Lista de HistorialIndependientes activos encontrados.
     */
    @Transactional(readOnly = true)
    @GetMapping("/cliente/{clienteId}/trabajo/{trabajoId}")
    public ResponseEntity<List<HistorialIndependientes>> getHistorialByClienteAndTrabajo(@PathVariable Long clienteId,
                                                                                         @PathVariable Long trabajoId) {
        List<HistorialIndependientes> historial = historialService.findByClienteAndTrabajo(clienteId, trabajoId);
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }

    /**
     * Listar todos los HistorialIndependientes, incluyendo inactivos.
     *
     * @return ResponseEntity con la lista de todos los HistorialIndependientes.
     */
    @Transactional(readOnly = true)
    @GetMapping("/todos")
    public ResponseEntity<List<HistorialIndependientes>> listarTodosHistorialIndependientes() {
        List<HistorialIndependientes> historial = historialService.getAllHistorialIndependientesIncludingInactive();
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }
}
