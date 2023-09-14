/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TallerMecanicoSpring.TM.rest;

import com.TallerMecanicoSpring.TM.model.Marca;
import com.TallerMecanicoSpring.TM.service.MarcaService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/marca/")
public class MarcaRest {
    @Autowired
    private MarcaService marcaService;
    
    @GetMapping
    private ResponseEntity<List<Marca>> getAllMarca(){
        return ResponseEntity.ok(this.marcaService.findAllActivos());
    } 
    
    @GetMapping("{name}")
    private ResponseEntity<List<Marca>> getMarcasByName(@PathVariable("name") String name){
        return ResponseEntity.ok(this.marcaService.findByName(name));
    } 
    
    @PostMapping
    private ResponseEntity<Marca> saveMarca(@RequestBody Marca marca){
        try{
            Marca marcaGuardado = this.marcaService.save(marca);
            return ResponseEntity.created(new URI("/marca/"+marcaGuardado.getId())).body(marcaGuardado);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping
    private ResponseEntity<Marca> updateMarca(@RequestBody Marca marca){
        try{
            Marca marcaGuardado = this.marcaService.updateMarca(marca);
            return ResponseEntity.created(new URI("/marca/"+marcaGuardado.getId())).body(marcaGuardado);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("delete/{id}")
    private ResponseEntity<Boolean> deleteById(@PathVariable ("id") Long id){
        this.marcaService.deleteById(id);
        return ResponseEntity.ok(marcaService.findById(id) == null);
    }
    
}

