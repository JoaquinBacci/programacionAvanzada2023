/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TallerMecanicoSpring.TM.rest;

import com.TallerMecanicoSpring.TM.model.Tecnico;
import com.TallerMecanicoSpring.TM.service.TecnicoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/tecnico/")
public class TecnicoRest {
    @Autowired
    private TecnicoService tecnicoService;
    
    @GetMapping
    private ResponseEntity<List<Tecnico>> getAllTecnico(){
        return ResponseEntity.ok(this.tecnicoService.findAll());
    }

    @GetMapping("activar/{id}")
    private ResponseEntity<Tecnico> reactivar(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.tecnicoService.activar(id));
    }
    
    @PostMapping("filtrar/")
    private ResponseEntity<List<Tecnico>> search(@RequestBody Tecnico tecnicoRq){
        return ResponseEntity.ok(this.tecnicoService.filtrarTecnicos(tecnicoRq));
    }
    
    @PutMapping("update/")
    private ResponseEntity<Tecnico> update(@RequestBody Tecnico tecnicoRq){
        return ResponseEntity.ok(this.tecnicoService.update(tecnicoRq));
    }
    
    @DeleteMapping("delete/{id}")
    private ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id){
        this.tecnicoService.deleteById(id);
        return ResponseEntity.ok(!(this.tecnicoService.getById(id) != null));
    }
    
    @PostMapping("save/")
    private ResponseEntity<Tecnico> saveTecnico(@RequestBody Tecnico tecnicoRq){
        return ResponseEntity.ok(this.tecnicoService.save(tecnicoRq));
    }
}
