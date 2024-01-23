/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.model.Cliente;
import com.TallerMecanicoSpring.TM.model.ClienteFiltrarRq;
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

    @Autowired
    private OrdenService ordenService ;

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

    public List<Cliente> findAllNOActivos() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        clientes = this.clienteRepository.findAll();

        List<Cliente> clientesActivos = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (!cliente.isActivo()) { 
                clientesActivos.add(cliente);
            }
        }

        return clientesActivos;
    }

    public List<Cliente> findAllActivos() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        clientes = this.clienteRepository.findAll();

        List<Cliente> clientesActivos = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.isActivo()) { 
                clientesActivos.add(cliente);
            }
        }

        return clientesActivos;
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
            //Crear nuevo Cliente
            List<Cliente> clientes = new ArrayList();
            clientes = this.clienteRepository.findAll();
            for(Cliente c: clientes){
                if(c.getDni() == entity.getDni()){
                   return (S) new Cliente();  
                }
            }

            /*Cliente c = new Cliente();
            c.setActivo(true);
            c.setApellido(entity.getApellido());
            c.setDireccion(entity.getDireccion());
            c.setNombre(entity.getNombre());
            c.setDni(entity.getDni());
            c.setEmail(entity.getEmail());
            c.setLicenciaConducir(entity.getLicenciaConducir());
            c.setNum_tel(entity.getNum_tel());
*/          entity.setActivo(true);
            return this.clienteRepository.save(entity);
        }
    }
    
    public Cliente update(Cliente c){
        return this.save(c);
    }
    
    public List<Cliente> filtrar(ClienteFiltrarRq clienteRq){

        System.out.println("FECHAS: " + clienteRq.getFechaDesde() + " " + clienteRq.getFechaHasta());

        List<Cliente> clientesRs = new ArrayList();
        
        clientesRs = this.findAllActivos();

        if(clienteRq.getFechaDesde() != null && !"".equals(clienteRq.getFechaDesde()) && clienteRq.getFechaHasta() != null && !"".equals(clienteRq.getFechaHasta())){
            System.out.println("FILTRO FECHA");
            clientesRs = this.ordenService.filtroEntreFecha(clienteRq.getFechaDesde(), clienteRq.getFechaHasta());
        }
        
        if (clienteRq.getNombre() == null || clienteRq.getNombre().trim().isEmpty()) {
            System.out.println("No se filtra por Nombre");
        } else {
            System.out.println("Filtro Nombre");
            clientesRs = clientesRs.stream()
                .filter(c -> c.getNombre() != null && c.getNombre().toLowerCase().contains(clienteRq.getNombre().toLowerCase()))
                .collect(Collectors.toList());
        }
        
        if (clienteRq.getApellido() == null || clienteRq.getApellido().trim().isEmpty()) {
            System.out.println("No se filtra por Apellido");
        } else {
            System.out.println("Filtro Apellido");
            clientesRs = clientesRs.stream()
                .filter(c -> c.getApellido() != null && c.getApellido().toLowerCase().contains(clienteRq.getApellido().toLowerCase()))
                .collect(Collectors.toList());
        }
        
        /* String dniString = String.valueOf(clienteRq.getDni());
        System.out.println("DNIStr: " + dniString); */

        if((clienteRq.getDni() == null)){
            System.out.println("No se filtra por DNI");
        } else {
            System.out.println("Filtro DNI");
            String dniString = String.valueOf(clienteRq.getDni());
            clientesRs = clientesRs.stream()
                .filter(c -> c.getDni() != null && c.getDni().toString().equals(dniString))
                .collect(Collectors.toList());

        }


        /* if (dniString == null || dniString.isEmpty()) {
            System.out.println("No se filtra por DNI");
        } else {
            System.out.println("Filtro DNI");
            clientesRs = clientesRs.stream()
                .filter(c -> c.getDni() != null && c.getDni().toString().equals(dniString))
                .collect(Collectors.toList());
        } */
        
        if (clienteRq.getNum_tel() == null || clienteRq.getNum_tel().trim().isEmpty()) {
            System.out.println("No se filtra por Teléfono");
        } else {
            System.out.println("Filtro Teléfono");
            clientesRs = clientesRs.stream()
                .filter(c -> c.getNum_tel().contains(clienteRq.getNum_tel()))
                .collect(Collectors.toList());
        }
        
        if (clienteRq.getEmail() == null || clienteRq.getEmail().trim().isEmpty()) {
            System.out.println("No se filtra por Email");
        } else {
            System.out.println("Filtro Email");
            clientesRs = clientesRs.stream()
                .filter(c -> c.getEmail() != null && c.getEmail().toLowerCase().contains(clienteRq.getEmail().toLowerCase()))
                .collect(Collectors.toList());
        }
        
        if (clienteRq.getDireccion() == null || clienteRq.getDireccion().trim().isEmpty()) {
            System.out.println("No se filtra por Dirección");
        } else {
            System.out.println("Filtro Dirección");
            clientesRs = clientesRs.stream()
                .filter(c -> c.getDireccion() != null && c.getDireccion().toLowerCase().contains(clienteRq.getDireccion().toLowerCase()))
                .collect(Collectors.toList());
        }
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

        Optional<Cliente> cliente = this.findById(id);
        if(cliente.isPresent()){
            cliente.get().setActivo(false);
            this.clienteRepository.save(cliente.get());
        } else{
            throw new UnsupportedOperationException("No se encontro el cliente a eliminar en la bd.");
        }    
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Cliente activar(Long id){
        Optional<Cliente> cliente = this.findById(id);
        if(cliente.isPresent()){
            cliente.get().setActivo(true);
            this.clienteRepository.save(cliente.get());
            return cliente.get();
        } else{
            throw new UnsupportedOperationException("No se encontro el cliente a activar en la bd.");
        }
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
