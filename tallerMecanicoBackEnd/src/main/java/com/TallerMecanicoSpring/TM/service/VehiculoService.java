/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.model.Cliente;
import com.TallerMecanicoSpring.TM.model.ClienteFiltrarRq;
import com.TallerMecanicoSpring.TM.model.Vehiculo;
import com.TallerMecanicoSpring.TM.repository.VehiculoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author maite
 */
@Service
public class VehiculoService implements VehiculoRepository{
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Vehiculo> S saveAndFlush(S entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Vehiculo> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAllInBatch(Iterable<Vehiculo> entities) {
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
    public Vehiculo getOne(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vehiculo getById(Long id) {
        return this.vehiculoRepository.getById(id);
    }

    @Override
    public Vehiculo getReferenceById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Vehiculo> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Vehiculo> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Vehiculo> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Vehiculo> findAll() {
        return this.vehiculoRepository.findAll();
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Optional<Vehiculo> activar(Long id){
        Optional<Vehiculo> vehiculo = this.findById(id);
        if(vehiculo.isPresent()){
            Vehiculo v = vehiculo.get();
            v.setActivo(true);
            return Optional.of(this.save(v));
        } else {
            throw new UnsupportedOperationException("El modelo no se encuentra en la BD.");
        }
    }
    
    public List<Vehiculo> getByClient(Long id){
        List<Vehiculo> vehiculos = new ArrayList();
        List<Vehiculo> vehiculosRs = new ArrayList();
        
        vehiculos = this.findAll();
        for(Vehiculo v : vehiculos){
            if(v.getCliente().getId() == id && v.isActivo()){
                vehiculosRs.add(v);
            }
        }
        
        return vehiculosRs;
    }

    @Override
    public List<Vehiculo> findAllById(Iterable<Long> ids) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Vehiculo> S save(S entity) {
        if(entity.getId() != null){
            //Editar el tecnico
            Optional<Vehiculo> vehiculoExistente = this.findById(entity.getId());

            if(vehiculoExistente.isPresent()){
                Vehiculo v = vehiculoExistente.get();
                v.setActivo(entity.isActivo());
                v.setKilometraje(entity.getKilometraje());
                v.setPatente(entity.getPatente());
                v.setMarca(entity.getMarca());
                v.setModelo(entity.getModelo());
                v.setCliente(entity.getCliente());
                
                
                return this.vehiculoRepository.save(entity);
            } else {
                return (S) new Vehiculo();
            }
            
            
        } else {
            //Crear nuevo Modelo
            List<Vehiculo> vehiculos = new ArrayList();
            vehiculos = this.vehiculoRepository.findAll();
            for(Vehiculo v: vehiculos){
                if(v.getPatente() == entity.getPatente()){
                   return (S) new Vehiculo();  
                }
            }
            entity.setActivo(true);
            return this.vehiculoRepository.save(entity);
        }
    }

    public Vehiculo updateVehiculo(Vehiculo vehiculoRq){
        return this.save(vehiculoRq);
    }

    public Page<Vehiculo> listarVehiculosPaginados(Pageable pageable, Vehiculo vehiculoRq) {
        
        List<Vehiculo> vehiculosRs = vehiculoRepository.findAll();

        vehiculosRs = this.findAll();

        vehiculosRs = vehiculosRs.stream()
                .filter(c -> c.isActivo() == vehiculoRq.isActivo())
                .collect(Collectors.toList());



        //Kilometraje
        if((vehiculoRq.getKilometraje() == null) ){
            System.out.println("No se filtra por Km");
        } else{
            String kmString = String.valueOf(vehiculoRq.getKilometraje());
            if (kmString != null || !kmString.equals("")) {
                System.out.println("Filtro KM");
                vehiculosRs = vehiculosRs.stream()
                    .filter(v -> String.valueOf(v.getKilometraje()).contains(kmString))
                    .collect(Collectors.toList());
            }
        }
        
        //patente
        if (vehiculoRq.getPatente() == null || vehiculoRq.getPatente().trim().equals("")) {
            System.out.println("No se filtra por Patente");
        } else{
            System.out.println("Filtro Patente");
            vehiculosRs = vehiculosRs.stream()
                .filter(v -> v.getPatente().contains(vehiculoRq.getPatente()))
                .collect(Collectors.toList());
        }
        
        //marca
        if(vehiculoRq.getMarca()==null || vehiculoRq.getMarca().getId() == null){
            System.out.println("No se filtra por marca");
            
        } else{
            System.out.println("Filtro Marca");
            vehiculosRs = vehiculosRs.stream()
                    .filter(v -> v.getMarca().getId() == vehiculoRq.getMarca().getId())
                    .collect(Collectors.toList());
        }
        
        //modelo
        if(vehiculoRq.getModelo() == null || vehiculoRq.getModelo().getId() == null){
            System.out.println("No se filtra por Modelo");
            
        } else{
            System.out.println("Fitro Modelo");
            vehiculosRs = vehiculosRs.stream()
                    .filter(v -> v.getModelo().getId() == vehiculoRq.getModelo().getId())
                    .collect(Collectors.toList());
        }
        
        //cliente
        if(vehiculoRq.getCliente() == null || vehiculoRq.getCliente().getId() == null){
            System.out.println("No se filtra por Cliente");
            
        } else {
            System.out.println("Filtro Cliente");
            String dniClienteStr = String.valueOf(vehiculoRq.getCliente().getDni());
            vehiculosRs = vehiculosRs.stream()
                    .filter(v -> v.getCliente().getNombre().toLowerCase().contains(vehiculoRq.getCliente().getNombre().toLowerCase())
                        || v.getCliente().getApellido().toLowerCase().contains(vehiculoRq.getCliente().getApellido().toLowerCase())
                        || String.valueOf(v.getCliente().getDni()).contains(dniClienteStr))
                    .collect(Collectors.toList());
        }
        
                // Create a Page<Cliente> from the filtered list
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Vehiculo> pageVehiculos;

        if (vehiculosRs.size() < startItem) {
            pageVehiculos = List.of(); // Return an empty list if the startItem is beyond the filtered list's size
        } else {
            int toIndex = Math.min(startItem + pageSize, vehiculosRs.size());
            pageVehiculos = vehiculosRs.subList(startItem, toIndex);
        }

        return new PageImpl<>(pageVehiculos, pageable, vehiculosRs.size());
    }

    
    public List<Vehiculo> filtarVehiculos(Vehiculo vehiculoRq){
        List<Vehiculo> vehiculos = new ArrayList();
        List<Vehiculo> vehiculosRs = new ArrayList();
        
        vehiculos = this.findAll();

        /* System.out.println("marca: " + vehiculoRq.getMarca());
        System.out.println("modelo: " + vehiculoRq.getModelo());
        System.out.println("cliente: " + vehiculoRq.getCliente()); */
        
        vehiculosRs = vehiculos.stream()
            .filter(v -> v.isActivo() == vehiculoRq.isActivo())
            .collect(Collectors.toList());
        
        //Kilometraje
        if((vehiculoRq.getKilometraje() == null) ){
            System.out.println("No se filtra por Km");
        } else{
            String kmString = String.valueOf(vehiculoRq.getKilometraje());
            if (kmString != null || !kmString.equals("")) {
                System.out.println("Filtro KM");
                vehiculosRs = vehiculosRs.stream()
                    .filter(v -> String.valueOf(v.getKilometraje()).contains(kmString))
                    .collect(Collectors.toList());
            }
        }
        
        //patente
        if (vehiculoRq.getPatente() == null || vehiculoRq.getPatente().trim().equals("")) {
            System.out.println("No se filtra por Patente");
        } else{
            System.out.println("Filtro Patente");
            vehiculosRs = vehiculosRs.stream()
                .filter(v -> v.getPatente().contains(vehiculoRq.getPatente()))
                .collect(Collectors.toList());
        }
        
        //marca
        if(vehiculoRq.getMarca()==null || vehiculoRq.getMarca().getId() == null){
            System.out.println("No se filtra por marca");
            
        } else{
            System.out.println("Filtro Marca");
            vehiculosRs = vehiculosRs.stream()
                    .filter(v -> v.getMarca().getId() == vehiculoRq.getMarca().getId())
                    .collect(Collectors.toList());
        }
        
        //modelo
        if(vehiculoRq.getModelo() == null || vehiculoRq.getModelo().getId() == null){
            System.out.println("No se filtra por Modelo");
            
        } else{
            System.out.println("Fitro Modelo");
            vehiculosRs = vehiculosRs.stream()
                    .filter(v -> v.getModelo().getId() == vehiculoRq.getModelo().getId())
                    .collect(Collectors.toList());
        }
        
        //cliente
        if(vehiculoRq.getCliente() == null || vehiculoRq.getCliente().getId() == null){
            System.out.println("No se filtra por Cliente");
            
        } else {
            System.out.println("Filtro Cliente");
            String dniClienteStr = String.valueOf(vehiculoRq.getCliente().getDni());
            vehiculosRs = vehiculosRs.stream()
                    .filter(v -> v.getCliente().getNombre().toLowerCase().contains(vehiculoRq.getCliente().getNombre().toLowerCase())
                        || v.getCliente().getApellido().toLowerCase().contains(vehiculoRq.getCliente().getApellido().toLowerCase())
                        || String.valueOf(v.getCliente().getDni()).contains(dniClienteStr))
                    .collect(Collectors.toList());
        }
        
        return vehiculosRs;
    }
    
    @Override
    public Optional<Vehiculo> findById(Long id) {
        System.out.println("======HOLA SOY EL SERVICE===========");
        System.out.println("EL ID QUE LLEGA ACA ES: " + id);
        return this.vehiculoRepository.findById(id);
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
        Optional<Vehiculo> vehiculo = this.findById(id);
        if(vehiculo.isPresent()){
            Vehiculo v = vehiculo.get();
            v.setActivo(false);
            this.save(v);
        } else {
            throw new UnsupportedOperationException("El vehiculo no se encuentra en la BD.");
        }
        //this.vehiculoRepository.deleteById(id);
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Vehiculo entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAll(Iterable<? extends Vehiculo> entities) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Vehiculo> findAll(Sort sort) {
        return this.vehiculoRepository.findAll(sort);
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Page<Vehiculo> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Vehiculo> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Vehiculo> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Vehiculo> long count(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Vehiculo> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <S extends Vehiculo, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
