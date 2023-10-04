package com.TallerMecanicoSpring.TM.rest;

import com.TallerMecanicoSpring.TM.model.DetalleOrden;
import com.TallerMecanicoSpring.TM.model.Servicio;
import com.TallerMecanicoSpring.TM.service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/detalleOrden")
public class DetalleOrdenRest {
    @Autowired
    DetalleOrdenService detalleOrdenService;

    //Método GET para detalle orden
    @GetMapping
    public ResponseEntity<List<DetalleOrden>> findAllDetallesOrden(){
        return ResponseEntity.ok(this.detalleOrdenService.findAllDetallesOrden());
    };

    //Método GET by  para detalle orden
    @GetMapping(path = "/{id}")
    public Optional<DetalleOrden> findByIdDetalleOrden(@PathVariable Long id){
        return this.detalleOrdenService.findById(id);
    }

    //Método POST para detalle orden
    @PostMapping("/save")
    private ResponseEntity<DetalleOrden> saveDetalleOrden(@RequestBody DetalleOrden detalleOrden){
        try{
            DetalleOrden ordenGuardada = this.detalleOrdenService.saveDetalleOrden(detalleOrden);
            return ResponseEntity.created(new URI("/detalleOrden/"+ordenGuardada.getId())).body(ordenGuardada);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
