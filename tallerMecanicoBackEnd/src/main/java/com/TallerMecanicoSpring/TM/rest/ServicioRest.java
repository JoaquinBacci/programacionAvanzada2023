package com.TallerMecanicoSpring.TM.rest;

import com.TallerMecanicoSpring.TM.model.Modelo;
import com.TallerMecanicoSpring.TM.model.Servicio;
import com.TallerMecanicoSpring.TM.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servicio")
public class ServicioRest {
    @Autowired
    ServicioService servicioService;

    //Método GET
    @GetMapping
    public ResponseEntity<List<Servicio>> findAllServicios(){
        return ResponseEntity.ok(this.servicioService.findAllServicios());
    };

    //Método GET by id
    @GetMapping(path = "/{id}")
    public Optional<Servicio> findById(@PathVariable Long id){
        return this.servicioService.findById(id);
    }

    //Método POST
    @PostMapping("/save")
    private ResponseEntity<Servicio> saveServicio(@RequestBody Servicio servicio){
        try{
            Servicio servicioGuardado = this.servicioService.saveServicio(servicio);
            return ResponseEntity.created(new URI("/servicio/"+servicioGuardado.getId())).body(servicioGuardado);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
