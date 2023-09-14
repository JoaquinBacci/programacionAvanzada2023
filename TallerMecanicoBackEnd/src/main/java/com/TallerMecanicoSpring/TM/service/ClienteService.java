/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.model.Cliente;
import com.TallerMecanicoSpring.TM.repository.ClienteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author maite
 */
@Service
public class ClienteService implements ClienteRepository{
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Cliente> S saveAndFlush(S entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Cliente> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteInBatch(Iterable<Cliente> entities) {
        ClienteRepository.super.deleteInBatch(entities); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void deleteAllInBatch(Iterable<Cliente> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cliente getOne(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cliente getById(Long id) {
        return this.clienteRepository.getById(id);
    }

    @Override
    public Cliente getReferenceById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Cliente> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Cliente> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Cliente> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Cliente> findAllById(Iterable<Long> ids) {
        return this.clienteRepository.findAllById(ids);
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Cliente> S save(S entity) {
        if(entity.getId() != null){
            //Editar el tecnico
            Optional<Cliente> clienteExistente = this.findById(entity.getId());

            if(clienteExistente.isPresent()){
                Cliente c = clienteExistente.get();
                c.setActivo(entity.isActivo());
                c.setNombre(entity.getNombre());
                c.setApellido(entity.getApellido());
                c.setDni(entity.getDni());
                c.setDireccion(entity.getDireccion());
                c.setEmail(entity.getEmail());
                c.setNum_tel(entity.getNum_tel());
                
                
                return this.clienteRepository.save(entity);
            } else {
                return (S) new Cliente();
            }
            
            
        } else {
            //Crear nuevo Tecnico
            List<Cliente> clientes = new ArrayList();
            clientes = this.clienteRepository.findAll();
            for(Cliente c: clientes){
                if(c.getDni() == entity.getDni()){
                   return (S) new Cliente();  
                }
            }
            return this.clienteRepository.save(entity);
        }
    }
    
    public Cliente update(Cliente c){
        return this.save(c);
    }
    
    public List<Cliente> filtrar(Cliente clienteRq){
        List<Cliente> clientesRs = new ArrayList();
        List<Cliente> clientes = new ArrayList();
        clientes = this.findAll();
        for(Cliente c : clientes){
           if(c.isActivo()){
               clientesRs.add(c);
           }
        }
        
        if( (clienteRq.getNombre()!= null) || !(clienteRq.getNombre().trim().equals("") )){
            clientesRs = clientesRs.stream()
                .filter(c -> c.getNombre().toLowerCase().contains(clienteRq.getNombre().toLowerCase()))
                .collect(Collectors.toList());
        }
        
        if((clienteRq.getApellido()!= null) || !(clienteRq.getApellido().trim().equals("") )){
            clientesRs = clientesRs.stream()
                .filter(c -> c.getApellido().toLowerCase().contains(clienteRq.getApellido().toLowerCase()))
                .collect(Collectors.toList());
        }
        
        String dniString = String.valueOf(clienteRq.getDni());
        if (dniString != null || !dniString.equals("")) {
            clientesRs = clientesRs.stream()
                .filter(c -> String.valueOf(c.getDni()).contains(dniString))
                .collect(Collectors.toList());
        }
        
        String telString = String.valueOf(clienteRq.getNum_tel());
        if (telString != null || !telString.equals("")) {
            clientesRs = clientesRs.stream()
                .filter(c -> String.valueOf(c.getNum_tel()).contains(telString))
                .collect(Collectors.toList());
        }
        
        if ( clienteRq.getEmail() != null || !clienteRq.getEmail().trim().equals("")) {
            clientesRs = clientesRs.stream()
                .filter(c -> c.getEmail().toLowerCase().contains(clienteRq.getEmail().toLowerCase()))
                .collect(Collectors.toList());
        }
        
        if (clienteRq.getDireccion() != null || !clienteRq.getDireccion().trim().equals("")){
            clientesRs = clientesRs.stream()
                .filter(c -> c.getDireccion().toLowerCase().contains(clienteRq.getDireccion().toLowerCase()))
                .collect(Collectors.toList());
        }
        
        //TODO: filtro por vehiculos
        
        return clientesRs;
    }
    
    @Override
    public Optional<Cliente> findById(Long id) {
        return this.clienteRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(Long id) {
        this.clienteRepository.deleteById(id);
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Cliente entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAll(Iterable<? extends Cliente> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Cliente> findAll(Sort sort) {
        return this.clienteRepository.findAll(sort);
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Cliente> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Cliente> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Cliente> long count(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Cliente> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Cliente, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
