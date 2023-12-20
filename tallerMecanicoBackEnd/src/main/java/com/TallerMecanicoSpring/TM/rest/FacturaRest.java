package com.TallerMecanicoSpring.TM.rest;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TallerMecanicoSpring.TM.model.Factura;
import com.TallerMecanicoSpring.TM.model.Orden;
import com.TallerMecanicoSpring.TM.service.FacturaService;


@RestController
@RequestMapping("/factura/")
public class FacturaRest {
    @Autowired
    FacturaService facturaService;

    @PostMapping("generate")
    private Factura generateFactura(@RequestBody Orden orden){
        return this.facturaService.save(orden.getId());
    }

   /*  @GetMapping("{id}")
    private ResponseEntity<Optional<Factura>> getFactura(@PathVariable Long id){
        return ResponseEntity.ok(this.facturaService.findByIdOrden(id));
    }*/
}
