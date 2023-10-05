package com.TallerMecanicoSpring.TM.rest;

import com.TallerMecanicoSpring.TM.model.Orden;
import com.TallerMecanicoSpring.TM.model.Servicio;
import com.TallerMecanicoSpring.TM.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orden")
public class OrdenRest {
    @Autowired
    OrdenService ordenService;

    //Método GET todas las ordenes
    @GetMapping
    public ResponseEntity<List<Orden>> findAllOrdenes(){
        return ResponseEntity.ok(this.ordenService.findAllOrdenes());
    };

    //Método GET by id para una orden
    @GetMapping(path = "/{id}")
    public Optional<Orden> findByIdOrden(@PathVariable Long id){
        return this.ordenService.findByIdOrden(id);
    }

    //Método POST para una orden
    @PostMapping("/save")
    private ResponseEntity<Orden> saveOrden(@RequestBody Orden orden){
        try{
            Orden ordenGuardada = this.ordenService.saveOrden(orden);
            return ResponseEntity.created(new URI("/servicio/"+ordenGuardada.getId())).body(ordenGuardada);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
