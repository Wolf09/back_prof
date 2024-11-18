package com.professional.controller;

import com.professional.model.dto.Error;
import com.professional.model.entities.Independiente;
import com.professional.model.entities.TrabajoIndependiente;
import com.professional.model.services.IndependienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/independiente")
public class IndependienteController {

    private final IndependienteService independienteService;

    @Autowired
    public IndependienteController(IndependienteService independienteService) {
        this.independienteService = independienteService;
    }

    @Transactional
    @PostMapping("/crear")
    public ResponseEntity<?> crearIndependiente(@Valid @RequestBody Independiente independiente, BindingResult result){
        if (result.hasErrors()){
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err ->{
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }
        Independiente creado = independienteService.createIndependiente(independiente);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    @GetMapping("/listar")
    public ResponseEntity<List<Independiente>> listarIndependientes(){
        List<Independiente> lista = independienteService.getAllIndependientes();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Nuevo Método: Obtener Independiente por ID solo cuando activo sea "true" caso contrario se emite
    // un mensaje diciendo que el Usuario no esta activo
    // Nuevo Método: Obtener Independiente por ID
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<Independiente> obtenerIndependientePorId(@PathVariable Long id){
        Independiente independiente = independienteService.getIndependienteById(id);
        return new ResponseEntity<>(independiente, HttpStatus.OK);
    }

    /**
     * Obtener todos los TrabajosIndependiente de un Independiente, incluyendo las calificaciones y los Clientes que las realizaron.
     *
     * @param id ID del Independiente.
     * @return ResponseEntity con la lista de TrabajosIndependiente y sus calificaciones.
     */
    @Transactional(readOnly = true)
    @GetMapping("/{id}/trabajos")
    public ResponseEntity<List<TrabajoIndependiente>> obtenerTrabajosIndependientes(@PathVariable Long id) {
        List<TrabajoIndependiente> trabajos = independienteService.getTrabajosIndependientesByIndependiente(id);
        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarIndependiente(@PathVariable Long id,
                                                     @Valid @RequestBody Independiente independienteDetalles,
                                                     BindingResult result){
        if (result.hasErrors()){
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err ->{
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }
        Independiente actualizado = independienteService.updateIndependiente(id, independienteDetalles);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/desactivar/{id}")
    public ResponseEntity<?> desactivarIndependiente(@PathVariable Long id){
        independienteService.deleteIndependiente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional(readOnly = true)
    @GetMapping("/listar-todos")
    public ResponseEntity<List<Independiente>> listarIndependientesTodos(){
        List<Independiente> lista = independienteService.getAllIndependientesTodos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


}
