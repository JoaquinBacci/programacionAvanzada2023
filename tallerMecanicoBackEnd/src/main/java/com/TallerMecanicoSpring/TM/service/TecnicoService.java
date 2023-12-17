/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.model.Tecnico;
import com.TallerMecanicoSpring.TM.repository.TecnicoRepository;
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
public class TecnicoService implements TecnicoRepository{
    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Tecnico> S saveAndFlush(S entity) {
        return null;
    }
    
    public <S extends Tecnico> Tecnico update(Tecnico tecnicoRq){
        return this.save(tecnicoRq);
    }

    @Override
    public <S extends Tecnico> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAllInBatch(Iterable<Tecnico> entities) {
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
    public Tecnico getOne(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Tecnico getById(Long id) {
       return this.tecnicoRepository.getById(id);
    }
    
    public List<Tecnico> filtrarTecnicos(Tecnico tecnicoRq){
        List<Tecnico> tecnicoRs = new ArrayList();
        List<Tecnico> tecnicos = new ArrayList();
        tecnicos = this.findAll();
        for(Tecnico t : tecnicos){
           if(t.isActivo()){
               tecnicoRs.add(t);
           }
        }
        
        System.out.println("Nombre: " + tecnicoRq.getNombre());
        System.out.println("Apellido: " + tecnicoRq.getApellido());
        System.out.println("DNI: " + tecnicoRq.getDni());
        System.out.println("direccion: " + tecnicoRq.getDireccion());
        System.out.println("mail: " + tecnicoRq.getEmail());
        System.out.println("tel: " + tecnicoRq.getNum_tel());
        System.out.println("legajo: " + tecnicoRq.getLegajo());
        
        
        if (tecnicoRq.getNombre() == null || tecnicoRq.getNombre().trim().isEmpty()) {
            System.out.println("No se filtra por Nombre");
        } else {
            System.out.println("Filtro Nombre");
            tecnicoRs = tecnicoRs.stream()
                .filter(t -> t.getNombre() != null && t.getNombre().toLowerCase().contains(tecnicoRq.getNombre().toLowerCase()))
                .collect(Collectors.toList());
        }

        if (tecnicoRq.getApellido() == null || tecnicoRq.getApellido().trim().isEmpty()) {
            System.out.println("No se filtra por Apellido");
        } else {
            System.out.println("Filtro Apellido");
            tecnicoRs = tecnicoRs.stream()
                .filter(t -> t.getApellido() != null && t.getApellido().toLowerCase().contains(tecnicoRq.getApellido().toLowerCase()))
                .collect(Collectors.toList());
        }

        if (tecnicoRq.getDni() == null) {
            System.out.println("No se filtra por DNI");
        } else {
            System.out.println("Filtro DNI");
            tecnicoRs = tecnicoRs.stream()
                .filter(t -> t.getDni() != null && t.getDni().toString().contains(tecnicoRq.getDni().toString()))
                .collect(Collectors.toList());
        }
        
        if (tecnicoRq.getNum_tel() == null || tecnicoRq.getNum_tel().trim().isEmpty()) {
            System.out.println("No se filtra por Teléfono");
        } else {
            System.out.println("Filtro Teléfono");
            tecnicoRs = tecnicoRs.stream()
                .filter(t -> t.getNum_tel() != null && t.getNum_tel().contains(tecnicoRq.getNum_tel().trim()))
                .collect(Collectors.toList());
        }

        if (tecnicoRq.getEmail() == null || tecnicoRq.getEmail().trim().isEmpty()) {
            System.out.println("No se filtra por Correo");
        } else {
            System.out.println("Filtro Correo");
            tecnicoRs = tecnicoRs.stream()
                .filter(t -> t.getEmail() != null && t.getEmail().toLowerCase().contains(tecnicoRq.getEmail().toLowerCase()))
                .collect(Collectors.toList());
        }

        if (tecnicoRq.getDireccion() == null || tecnicoRq.getDireccion().trim().isEmpty()) {
            System.out.println("No se filtra por Dirección");
        } else {
            System.out.println("Filtro Dirección");
            tecnicoRs = tecnicoRs.stream()
                .filter(t -> t.getDireccion() != null && t.getDireccion().toLowerCase().contains(tecnicoRq.getDireccion().toLowerCase()))
                .collect(Collectors.toList());
        }

        if (tecnicoRq.getLegajo() == null) {
            System.out.println("No se filtra por Legajo");
        } else {
            System.out.println("Filtro Legajo");
            tecnicoRs = tecnicoRs.stream()
                .filter(t -> t.getLegajo() != null && t.getLegajo().toString().contains(tecnicoRq.getLegajo().toString()))
                .collect(Collectors.toList());
        }
        
        return tecnicoRs;
    }

    @Override
    public Tecnico getReferenceById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Tecnico> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Tecnico> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Tecnico> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Tecnico> findAll() {
        return this.tecnicoRepository.findAll();
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Tecnico> findAllById(Iterable<Long> ids) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Tecnico> S save(S entity) {
        if (entity.getDni() == null) {
            throw new IllegalArgumentException("El DNI del técnico no puede ser nulo");
        }
        if(entity.getId() != null){
            //Editar el tecnico
            Optional<Tecnico> tecnicoExistente = this.findById(entity.getId());

            if(tecnicoExistente.isPresent()){
                Tecnico t = tecnicoExistente.get();
                t.setActivo(entity.isActivo());
                t.setNombre(entity.getNombre());
                t.setApellido(entity.getApellido());
                t.setDni(entity.getDni());
                t.setDireccion(entity.getDireccion());
                t.setEmail(entity.getEmail());
                t.setLegajo(entity.getLegajo());
                t.setNum_tel(entity.getNum_tel());
                
                
                return this.tecnicoRepository.save(entity);
            } else {
                return (S) new Tecnico();
            }
            
            
        } else {
            //Crear nuevo Tecnico
            List<Tecnico> tecnicos = new ArrayList();
            tecnicos = this.tecnicoRepository.findAll();
            for(Tecnico tecnico: tecnicos){
                if(tecnico.getDni() == entity.getDni()){
                   return (S) new Tecnico();  
                }
            }
            entity.setActivo(true);
            return this.tecnicoRepository.save(entity);
        }
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Tecnico> findById(Long id) {
        return this.tecnicoRepository.findById(id);
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
        this.tecnicoRepository.deleteById(id);
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Tecnico entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAll(Iterable<? extends Tecnico> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Tecnico> findAll(Sort sort) {
        return this.tecnicoRepository.findAll(sort);
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Page<Tecnico> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Tecnico> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Tecnico> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Tecnico> long count(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Tecnico> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Tecnico, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Optional<Tecnico> findByEmail(String email) {
        Tecnico tecnico = null;
        List<Tecnico> listaDeTecnicos = new ArrayList();
        listaDeTecnicos = this.findAll();
        // Utiliza un for each en lugar de un bucle for para recorrer los técnicos
        for (Tecnico t : listaDeTecnicos) {
            if (t.getEmail().equalsIgnoreCase(email)) {
                tecnico = t;
                break;
            }
        }
        // Devuelve el Optional con el técnico encontrado, o vacío si no se encuentra
        return Optional.ofNullable(tecnico);
    }
}
