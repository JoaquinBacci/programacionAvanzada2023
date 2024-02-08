/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TallerMecanicoSpring.TM.rest;

import com.TallerMecanicoSpring.TM.dto.PaginatedResponse;
import com.TallerMecanicoSpring.TM.model.Cliente;
import com.TallerMecanicoSpring.TM.model.ClienteFiltrarRq;
import com.TallerMecanicoSpring.TM.service.ClienteService;
import java.util.List;

import jakarta.validation.Valid;

import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/cliente/")
public class ClienteRest {
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    private ResponseEntity<List<Cliente>> getAllClientes(){
        return ResponseEntity.ok(clienteService.findAllActivos());
    }

    @GetMapping("desactivados/")
    private ResponseEntity<List<Cliente>> getAllClientesDesactivados(){
        return ResponseEntity.ok(clienteService.findAllNOActivos());
    }

    @GetMapping("activar/{id}")
    private ResponseEntity<Cliente> activarCliente(@PathVariable("id") Long id){
        System.out.println("ID EN REST: " + id);
        return ResponseEntity.ok(clienteService.activar(id));
    }

    @PostMapping("listar/")
    public ResponseEntity<Page<Cliente>> listarClientes(@RequestBody ClienteFiltrarRq clienteRq, Pageable pageable) {
        Page<Cliente> page = clienteService.listarClientesPaginados(pageable, clienteRq);
        return ResponseEntity.ok(page);
    }
    
    @PostMapping("filtrar/")
    private ResponseEntity<List<Cliente>> search(@RequestBody ClienteFiltrarRq clienteRq){
        return ResponseEntity.ok(this.clienteService.filtrar(clienteRq));
    }
    
    @PutMapping("update/")
    private ResponseEntity<Cliente> update(@RequestBody Cliente clienteRq){
        return ResponseEntity.ok(this.clienteService.update(clienteRq));
    }
    
    @DeleteMapping("delete/{id}")
    private ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id){
        this.clienteService.deleteById(id);
        return ResponseEntity.ok(!(this.clienteService.getById(id) != null));
    }
    
    @PostMapping("save/")
    private ResponseEntity<Cliente> saveCliente(@Valid @RequestBody Cliente clienteRq){
        return ResponseEntity.ok(this.clienteService.save(clienteRq));
    }

    
}
