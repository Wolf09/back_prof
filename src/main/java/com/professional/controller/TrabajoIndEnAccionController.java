package com.professional.controller;

import com.professional.model.dto.ActualizarEstadoTrabajoDTO;
import com.professional.model.dto.Error;
import com.professional.model.dto.TrabajoEnAccionDTO;
import com.professional.model.entities.Cliente;
import com.professional.model.enums.EstadoTrabajo;
import com.professional.model.entities.TrabajoIndEnAccion;
import com.professional.model.entities.TrabajoIndependiente;
import com.professional.model.services.ClienteService;
import com.professional.model.services.EmpresaService;
import com.professional.model.services.TrabajoIndEnAccionService;
import com.professional.model.services.TrabajoIndependienteService;
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
 * Controlador para gestionar las operaciones CRUD de la entidad TrabajoIndEnAccion.
 */
@RestController
@RequestMapping("/trabajo-ind-en-accion")
public class TrabajoIndEnAccionController {

    private final TrabajoIndEnAccionService trabajoIndEnAccionService;
    private final TrabajoIndependienteService trabajoIndependienteService;
    private final ClienteService clienteService;
    private final EmpresaService empresaService;

    @Autowired
    public TrabajoIndEnAccionController(TrabajoIndEnAccionService trabajoIndEnAccionService,
                                        TrabajoIndependienteService trabajoIndependienteService,
                                        ClienteService clienteService,
                                        EmpresaService empresaService) {
        this.trabajoIndEnAccionService = trabajoIndEnAccionService;
        this.trabajoIndependienteService = trabajoIndependienteService;
        this.clienteService = clienteService;
        this.empresaService = empresaService;
    }

    /**
     * Crear un nuevo TrabajoIndEnAccion.
     *
     * @param trabajoEnAccion Datos del TrabajoIndEnAccion a crear.
     * @param result          Resultado de la validación.
     * @return ResponseEntity con el TrabajoIndEnAccion creado o errores de validación.
     */
    @Transactional
    @PostMapping("/crear")
    public ResponseEntity<?> crearTrabajoEnAccion(@Valid @RequestBody TrabajoIndEnAccion trabajoEnAccion, BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        // Asegurar que 'activo' esté establecido en true
        trabajoEnAccion.setActivo(true);

        TrabajoIndEnAccion creado = trabajoIndEnAccionService.createTrabajoEnAccion(trabajoEnAccion);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    /**
     * Listar todos los TrabajosIndEnAccion activos.
     *
     * @return ResponseEntity con la lista de TrabajosIndEnAccion activos.
     */
    @Transactional(readOnly = true)
    @GetMapping("/listar")
    public ResponseEntity<List<TrabajoIndEnAccion>> listarTrabajosEnAccionActivos() {
        List<TrabajoIndEnAccion> listaActivos = trabajoIndEnAccionService.getAllTrabajosEnAccionActivos();
        return new ResponseEntity<>(listaActivos, HttpStatus.OK);
    }

    /**
     * Listar todos los TrabajosIndEnAccion, tanto activos como inactivos.
     *
     * @return ResponseEntity con la lista completa de TrabajosIndEnAccion.
     */
    @Transactional(readOnly = true)
    @GetMapping("/listar-todos")
    public ResponseEntity<List<TrabajoIndEnAccion>> listarTrabajosEnAccionTodos() {
        List<TrabajoIndEnAccion> lista = trabajoIndEnAccionService.getAllTrabajosEnAccion();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    /**
     * Obtener un TrabajoIndEnAccion por su ID.
     *
     * @param id ID del TrabajoIndEnAccion.
     * @return ResponseEntity con el TrabajoIndEnAccion encontrado.
     */
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<TrabajoIndEnAccion> obtenerTrabajoEnAccionPorId(@PathVariable Long id) {
        TrabajoIndEnAccion trabajoEnAccion = trabajoIndEnAccionService.getTrabajoEnAccionById(id);
        return new ResponseEntity<>(trabajoEnAccion, HttpStatus.OK);
    }

    /**
     * Actualizar un TrabajoIndEnAccion existente.
     *
     * @param id                    ID del TrabajoIndEnAccion a actualizar.
     * @param trabajoEnAccionDetalles Datos actualizados del TrabajoIndEnAccion.
     * @param result                Resultado de la validación.
     * @return ResponseEntity con el TrabajoIndEnAccion actualizado o errores de validación.
     */
    @Transactional
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarTrabajoEnAccion(@PathVariable Long id,
                                                       @Valid @RequestBody TrabajoIndEnAccion trabajoEnAccionDetalles,
                                                       BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        TrabajoIndEnAccion actualizado = trabajoIndEnAccionService.updateTrabajoEnAccion(id, trabajoEnAccionDetalles);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    /**
     * Eliminar lógicamente un TrabajoIndEnAccion por su ID.
     *
     * @param id ID del TrabajoIndEnAccion a eliminar.
     * @return ResponseEntity con el estado de la operación.
     */
    @Transactional
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTrabajoEnAccion(@PathVariable Long id) {
        trabajoIndEnAccionService.deleteTrabajoEnAccion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Buscar TrabajosIndEnAccion por estado de trabajo.
     *
     * @param estado Estado del trabajo.
     * @return ResponseEntity con la lista de TrabajosIndEnAccion que coinciden.
     */
    @Transactional(readOnly = true)
    @GetMapping("/buscar-por-estado")
    public ResponseEntity<List<TrabajoIndEnAccion>> buscarPorEstadoTrabajo(@RequestParam EstadoTrabajo estado) {
        List<TrabajoIndEnAccion> trabajos = trabajoIndEnAccionService.findByEstadoTrabajo(estado);
        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

    /**
     * Buscar TrabajosIndEnAccion por TrabajoIndependiente.
     *
     * @param trabajoIndependienteId ID del TrabajoIndependiente.
     * @return ResponseEntity con la lista de TrabajosIndEnAccion asociados.
     */
    @Transactional(readOnly = true)
    @GetMapping("/trabajo-independiente/{trabajoIndependienteId}")
    public ResponseEntity<List<TrabajoIndEnAccion>> buscarPorTrabajoIndependiente(@PathVariable Long trabajoIndependienteId) {
        TrabajoIndependiente trabajoIndependiente = trabajoIndependienteService.getTrabajoIndependienteById(trabajoIndependienteId);
        List<TrabajoIndEnAccion> trabajos = trabajoIndEnAccionService.getTrabajosEnAccionByTrabajoIndependiente(trabajoIndependiente);

        // Filtrar solo los activos
        List<TrabajoIndEnAccion> trabajosActivos = trabajos.stream()
                .filter(TrabajoIndEnAccion::getActivo)
                .toList();

        return new ResponseEntity<>(trabajosActivos, HttpStatus.OK);
    }

    /**
     * Buscar TrabajosIndEnAccion por Cliente.
     *
     * @param clienteId ID del Cliente.
     * @return ResponseEntity con la lista de TrabajosIndEnAccion asociados.
     */
    @Transactional(readOnly = true)
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<TrabajoIndEnAccion>> buscarPorCliente(@PathVariable Long clienteId) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        List<TrabajoIndEnAccion> trabajos = trabajoIndEnAccionService.getTrabajosEnAccionByCliente(cliente);

        // Filtrar solo los activos
        List<TrabajoIndEnAccion> trabajosActivos = trabajos.stream()
                .filter(TrabajoIndEnAccion::getActivo)
                .toList();

        return new ResponseEntity<>(trabajosActivos, HttpStatus.OK);
    }

    /**
     * Buscar todos los TrabajosIndEnAccion, independientemente de su estado activo.
     *
     * @return ResponseEntity con la lista completa de TrabajosIndEnAccion.
     */
    @Transactional(readOnly = true)
    @GetMapping("/buscar-todos")
    public ResponseEntity<List<TrabajoIndEnAccion>> buscarTodosTrabajosEnAccion() {
        List<TrabajoIndEnAccion> trabajos = trabajoIndEnAccionService.getAllTrabajosEnAccion();
        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

    /**
     * Actualizar el EstadoTrabajo de un TrabajoIndEnAccion específico.
     *
     * @param id                     ID del TrabajoIndEnAccion a actualizar.
     * @param actualizarEstadoTrabajoDTO DTO que contiene el nuevo estado.
     * @param result                 Resultado de la validación.
     * @return ResponseEntity con el TrabajoEnAccionDTO actualizado o errores de validación.
     */
    @Transactional
    @PutMapping("/actualizar-estado/{id}")
    public ResponseEntity<?> actualizarEstadoTrabajo(@PathVariable Long id,
                                                     @Valid @RequestBody ActualizarEstadoTrabajoDTO actualizarEstadoTrabajoDTO,
                                                     BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        try {
            TrabajoEnAccionDTO actualizado = trabajoIndEnAccionService.updateEstadoTrabajo(id, actualizarEstadoTrabajoDTO.getEstadoTrabajo());
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
}
