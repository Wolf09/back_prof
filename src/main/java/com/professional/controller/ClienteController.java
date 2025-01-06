package com.professional.controller;

import com.professional.model.dto.Error;
import com.professional.model.entities.Cliente;
import com.professional.model.enums.RangoCalificacion;
import com.professional.model.services.ClienteService;
import com.professional.model.dto.TrabajoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


//TODO cambiar todos los metodos a Optional<> la orden ya esta hecha a la IA
/**
 * Controlador para gestionar las operaciones CRUD de la entidad Cliente.
 */
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Crear un nuevo Cliente.
     *
     * @param cliente Datos del Cliente a crear.
     * @param result  Resultado de la validación.
     * @return ResponseEntity con el Cliente creado o errores de validación.
     */
    @Transactional
    @PostMapping("/crear")
    public ResponseEntity<?> crearCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        Cliente creado = clienteService.createCliente(cliente);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    /**
     * Listar todos los Clientes activos.
     *
     * @return ResponseEntity con la lista de Clientes activos.
     */
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientesActivos() {
        List<Cliente> clientesActivos = clienteService.getAllClientes();
        return new ResponseEntity<>(clientesActivos, HttpStatus.OK);
    }

    /**
     * Listar todos los Clientes, incluyendo los inactivos.
     *
     * @return ResponseEntity con la lista completa de Clientes.
     */
    @Transactional(readOnly = true)
    @GetMapping("/todos")
    public ResponseEntity<List<Cliente>> listarTodosClientes() {
        List<Cliente> todosClientes = clienteService.getAllClientesTodos();
        return new ResponseEntity<>(todosClientes, HttpStatus.OK);
    }

    /**
     * Obtener un Cliente por su ID.
     *
     * @param id ID del Cliente.
     * @return ResponseEntity con el Cliente encontrado.
     */
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    /**
     * Actualizar un Cliente existente.
     *
     * @param id           ID del Cliente a actualizar.
     * @param clienteDetalles Datos actualizados del Cliente.
     * @param result       Resultado de la validación.
     * @return ResponseEntity con el Cliente actualizado o errores de validación.
     */
    @Transactional
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id,
                                               @Valid @RequestBody Cliente clienteDetalles,
                                               BindingResult result) {
        if (result.hasErrors()) {
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err -> {
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        Cliente actualizado = clienteService.updateCliente(id, clienteDetalles);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    /**
     * Eliminar lógicamente un Cliente por su ID.
     *
     * @param id ID del Cliente a eliminar.
     * @return ResponseEntity con el estado de la operación.
     */
    @Transactional
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Buscar Clientes por su correo electrónico.
     *
     * @param correo Correo electrónico del Cliente.
     * @return ResponseEntity con el Cliente encontrado.
     */
    @Transactional(readOnly = true)
    @GetMapping("/buscar-por-correo")
    public ResponseEntity<Cliente> buscarClientePorCorreo(@RequestParam String correo) {
        Cliente cliente = clienteService.getClienteByCorreo(correo);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    /**
     * Buscar Clientes por sus nombres (contiene, ignore case).
     *
     * @param nombres Nombres a buscar.
     * @return ResponseEntity con la lista de Clientes que coinciden.
     */
    @Transactional(readOnly = true)
    @GetMapping("/buscar-por-nombres")
    public ResponseEntity<List<Cliente>> buscarClientesPorNombres(@RequestParam String nombres) {
        List<Cliente> clientes = clienteService.findByNombresContainingIgnoreCase(nombres);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    /**
     * Buscar Clientes por su estado activo.
     *
     * @param activo Estado activo del Cliente.
     * @return ResponseEntity con la lista de Clientes que coinciden.
     */
    @Transactional(readOnly = true)
    @GetMapping("/buscar-por-activo")
    public ResponseEntity<List<Cliente>> buscarClientesPorActivo(@RequestParam Boolean activo) {
        List<Cliente> clientes = clienteService.findByActivo(activo);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    /**
     * Endpoint para listar trabajos por descripción y rango de calificación.
     *
     * @param descripcion Parte de la descripción a buscar.
     * @param rango       Rango de calificación.
     * @return Lista de TrabajoDTO que coinciden con los criterios.
     */
    @GetMapping("/trabajos/buscar")
    public ResponseEntity<List<TrabajoDTO>> buscarTrabajosPorDescripcionYRango(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("rango") RangoCalificacion rango) {

        List<TrabajoDTO> trabajos = clienteService.listarTrabajosPorDescripcionYRangoCalificacion(descripcion, rango);
        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

    /**
     * Endpoint para listar trabajos por descripción y precio Ascendente
     *
     * @param descripcion Parte de la descripción a buscar.
     * @return Lista de TrabajoDTO que coinciden con los criterios.
     */
    @GetMapping("/trabajos/buscar/precio-asc")
    public ResponseEntity<List<TrabajoDTO>> buscarTrabajosPorPrecioAsc(
            @RequestParam("descripcion") String descripcion) {

        List<TrabajoDTO> trabajos = clienteService.listarTrabajosPorDescripcionOrdenadosPorPrecioAsc(descripcion);
        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

    /**
     * Endpoint para listar trabajos por descripción y precio Decendente
     *
     * @param descripcion Parte de la descripción a buscar.
     * @return Lista de TrabajoDTO que coinciden con los criterios.
     */
    @GetMapping("/trabajos/buscar/precio-desc")
    public ResponseEntity<List<TrabajoDTO>> buscarTrabajosPorPrecioDesc(
            @RequestParam("descripcion") String descripcion) {

        List<TrabajoDTO> trabajos = clienteService.listarTrabajosPorDescripcionOrdenadosPorPrecioDesc(descripcion);
        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

    /**
     * Endpoint para listar trabajos por descripción y fecha Ascendente
     *
     * @param descripcion Parte de la descripción a buscar.
     * @return Lista de TrabajoDTO que coinciden con los criterios.
     */
    @GetMapping("/trabajos/buscar/fecha-asc")
    public ResponseEntity<List<TrabajoDTO>> buscarTrabajosPorFechaAsc(
            @RequestParam("descripcion") String descripcion) {

        List<TrabajoDTO> trabajos = clienteService.listarTrabajosPorDescripcionOrdenadosPorFechaCreacionAsc(descripcion);
        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

}
