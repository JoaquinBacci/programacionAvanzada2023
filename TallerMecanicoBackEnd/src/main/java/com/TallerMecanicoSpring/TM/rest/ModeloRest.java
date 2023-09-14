/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TallerMecanicoSpring.TM.rest;

import com.TallerMecanicoSpring.TM.model.Modelo;
import com.TallerMecanicoSpring.TM.service.ModeloService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author maite
 */
@RestController
@RequestMapping("/modelo/")
public class ModeloRest {
    @Autowired
    private ModeloService modeloService;
    
    @GetMapping
    private ResponseEntity<List<Modelo>> getAllModelos(){
        return ResponseEntity.ok(this.modeloService.findAll());
    }
    
    @GetMapping("modeloXmarca/{id_marca}")
    private ResponseEntity<List<Modelo>> getModelosByMarca(@PathVariable("id_marca") Long id_marca){
        return ResponseEntity.ok(this.modeloService.findAllByMarca(id_marca));
    }
    
    
    @PostMapping("save/")
    private ResponseEntity<Modelo> saveModelo(@RequestBody Modelo modelo){
        try{
            Modelo modeloGuardado = this.modeloService.save(modelo);
            return ResponseEntity.created(new URI("/modelo/"+modeloGuardado.getId())).body(modeloGuardado);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PostMapping("buscar/")
    private ResponseEntity<List<Modelo>> search(@RequestBody Modelo modeloRq){
        return ResponseEntity.ok(this.modeloService.filtrarModelos(modeloRq));
    }
    
    @DeleteMapping("delete/{id}")
    private ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id){
        this.modeloService.deleteById(id);
        return ResponseEntity.ok(this.modeloService.getById(id)==null);
    }
    
    @PutMapping("update/")
    private ResponseEntity<Modelo> update(@RequestBody Modelo modeloRq){
        return ResponseEntity.ok(this.modeloService.update(modeloRq));
    }
}
