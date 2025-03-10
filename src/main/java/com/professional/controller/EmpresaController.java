package com.professional.controller;

import com.professional.model.dto.Error;
import com.professional.model.entities.Empresa;
import com.professional.model.services.EmpresaService;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("empresa")
public class EmpresaController {
    private final EmpresaService empresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }


    @Transactional
    @PostMapping("/crear")
    public ResponseEntity<?> crearEmpresa(@Valid @RequestBody Empresa empresa, BindingResult result){
        if (result.hasErrors()){
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err ->{
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }
        Empresa creado=empresaService.createEmpresa(empresa);
        return new ResponseEntity<>(creado,HttpStatus.CREATED);
    }
    @Transactional(readOnly = true)
    @GetMapping("/listar")
    public ResponseEntity<List<Empresa>> listarEmpresas(){
        List<Empresa> lista= empresaService.getAllEmpresas();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerEmpresaPorId(@PathVariable Long id){
        Empresa empresa= empresaService.getEmpresaById(id);
        return new ResponseEntity<>(empresa,HttpStatus.OK);
    }
    @Transactional
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEmpresa(@PathVariable Long id,
                                               @Valid @RequestBody Empresa empresa,
                                               BindingResult result){
        if (result.hasErrors()){
            List<Error> errores = new ArrayList<>();
            result.getFieldErrors().forEach(err ->{
                errores.add(new Error(err.getDefaultMessage()));
            });
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }
        Empresa actualizado= empresaService.updateEmpresa(id,empresa);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);

    }

    @Transactional
    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<?> eliminarEmpresa(@PathVariable Long id){
        empresaService.deleteEmpresa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional(readOnly = true)
    @GetMapping("/listar-todos")
    public ResponseEntity<List<Empresa>> listarEmpresasTodos(){
        List<Empresa> lista= empresaService.getAllEmpresasTodos();
        return new ResponseEntity<>(lista,HttpStatus.OK);
    }

}
