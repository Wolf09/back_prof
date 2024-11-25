package com.professional.controller;

import com.professional.model.entities.HistorialEmpresas;
import com.professional.model.dto.Error;
import com.professional.model.services.HistorialEmpresasService;
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
@RequestMapping("/historial-empresas")
public class HistorialEmpresasController {

    private final HistorialEmpresasService historialEmpresasService;

    @Autowired
    public HistorialEmpresasController(HistorialEmpresasService historialEmpresasService) {
        this.historialEmpresasService = historialEmpresasService;
    }

    /**
     * Crear un nuevo HistorialEmpresas.
     *
     * @param historial Objeto HistorialEmpresas a crear.
     * @param result    Resultado de la validación.
     * @return ResponseEntity con el HistorialEmpresas creado o lista de errores.
     */
    @Transactional
    @PostMapping
    public ResponseEntity<?> crearHistorialEmpresas(@Valid @RequestBody HistorialEmpresas historial, BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        try {
            HistorialEmpresas creado = historialEmpresasService.createHistorialEmpresas(historial);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (Exception ex) {
            List<Error> errores = new ArrayList<>();
            errores.add(new Error(ex.getMessage()));
            return new ResponseEntity<>(errores, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Listar todos los HistorialEmpresas activos.
     *
     * @return ResponseEntity con la lista de HistorialEmpresas activos.
     */
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<HistorialEmpresas>> listarHistorialEmpresasActivos() {
        List<HistorialEmpresas> historial = historialEmpresasService.getAllHistorialEmpresas();
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }

    /**
     * Listar todos los HistorialEmpresas, incluyendo inactivos.
     *
     * @return ResponseEntity con la lista de todos los HistorialEmpresas.
     */
    @Transactional(readOnly = true)
    @GetMapping("/todos")
    public ResponseEntity<List<HistorialEmpresas>> listarTodosHistorialEmpresas() {
        List<HistorialEmpresas> historial = historialEmpresasService.getAllHistorialEmpresasIncludingInactive();
        return new ResponseEntity<>(historial, HttpStatus.OK);
    }

    /**
     * Obtener un HistorialEmpresas por su ID (solo si está activo).
     *
     * @param id ID del HistorialEmpresas.
     * @return HistorialEmpresas encontrado.
     */
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<?> getHistorialEmpresasById(@PathVariable Long id) {
        try {
            HistorialEmpresas historial = historialEmpresasService.getHistorialEmpresasById(id);
            return new ResponseEntity<>(historial, HttpStatus.OK);
        } catch (Exception ex) {
            List<Error> errores = new ArrayList<>();
            errores.add(new Error(ex.getMessage()));
            if (ex instanceof com.professional.model.exceptions.ResourceNotFoundException) {
                return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(errores, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualizar un HistorialEmpresas existente.
     *
     * @param id        ID del HistorialEmpresas a actualizar.
     * @param historial Datos actualizados del HistorialEmpresas.
     * @param result    Resultado de la validación.
     * @return HistorialEmpresas actualizado o lista de errores.
     */
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateHistorialEmpresas(@PathVariable Long id,
                                                     @Valid @RequestBody HistorialEmpresas historial,
                                                     BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        try {
            HistorialEmpresas actualizado = historialEmpresasService.updateHistorialEmpresas(id, historial);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (Exception ex) {
            List<Error> errores = new ArrayList<>();
            errores.add(new Error(ex.getMessage()));
            if (ex instanceof com.professional.model.exceptions.ResourceNotFoundException) {
                return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(errores, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Eliminar (lógicamente) un HistorialEmpresas por su ID.
     *
     * @param id ID del HistorialEmpresas a eliminar.
     * @return Respuesta con mensaje de éxito o errores.
     */
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHistorialEmpresas(@PathVariable Long id) {
        try {
            historialEmpresasService.deleteHistorialEmpresas(id);
            return new ResponseEntity<>("HistorialEmpresas eliminado correctamente.", HttpStatus.OK);
        } catch (Exception ex) {
            List<Error> errores = new ArrayList<>();
            errores.add(new Error(ex.getMessage()));
            if (ex instanceof com.professional.model.exceptions.ResourceNotFoundException ||
                    ex instanceof IllegalStateException) {
                return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(errores, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Buscar HistorialEmpresas por Cliente y TrabajoEmpresa activos.
     *
     * @param clienteId ID del Cliente.
     * @param trabajoId ID del TrabajoEmpresa.
     * @return Lista de HistorialEmpresas activos encontrados.
     */
    @Transactional(readOnly = true)
    @GetMapping("/cliente/{clienteId}/trabajo/{trabajoId}")
    public ResponseEntity<?> getHistorialByClienteAndTrabajo(@PathVariable Long clienteId,
                                                             @PathVariable Long trabajoId) {
        try {
            List<HistorialEmpresas> historiales = historialEmpresasService.findByClienteAndTrabajo(clienteId, trabajoId);
            return new ResponseEntity<>(historiales, HttpStatus.OK);
        } catch (Exception ex) {
            List<Error> errores = new ArrayList<>();
            errores.add(new Error(ex.getMessage()));
            if (ex instanceof com.professional.model.exceptions.ResourceNotFoundException) {
                return new ResponseEntity<>(errores, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(errores, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
