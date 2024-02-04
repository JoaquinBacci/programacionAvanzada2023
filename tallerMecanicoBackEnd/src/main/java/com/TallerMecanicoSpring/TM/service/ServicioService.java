package com.TallerMecanicoSpring.TM.service;


import com.TallerMecanicoSpring.TM.model.Marca;
import com.TallerMecanicoSpring.TM.model.Servicio;
import com.TallerMecanicoSpring.TM.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicioService{
     @Autowired
    ServicioRepository servicioRepository;

     public List<Servicio> findAllServicios(){
         return servicioRepository.findAll();
     }

     public Optional<Servicio> findByIdServicio(Long id){
         return servicioRepository.findById(id);
     }
    
    public Optional<Servicio> activar(Long id){
        Optional<Servicio> service = this.findByIdServicio(id);
        if(service.isPresent()){
            Servicio s = service.get();
            s.setActivo(true);
            return Optional.of(this.saveServicio(s));
        } else {
            throw new UnsupportedOperationException("El servicio no se encuentra en la BD.");
        }
    }
    
    public Servicio updateServicio(Servicio s){
        return this.saveServicio(s);
    }

    public Servicio saveServicio(Servicio servicioRq) {
        if (servicioRq.getId() != null) {
            Optional<Servicio> servicioExistente = this.findByIdServicio(servicioRq.getId());
    
            if (servicioExistente.isPresent()) {
                Servicio s = servicioExistente.get();
    
                s.setActivo(servicioRq.isActivo());
                s.setNombre(servicioRq.getNombre());
                s.setDescripcion(servicioRq.getDescripcion());
                s.setPrecio(servicioRq.getPrecio());
                s.setImpuesto(servicioRq.getImpuesto());
    
                return this.servicioRepository.save(s);
            } else {
                return null;
            }
        } else {
            List<Servicio> servicios = this.findAllServicios();
            for (Servicio s : servicios) {
                if (s.getNombre().trim().toLowerCase().equalsIgnoreCase(servicioRq.getNombre())) {
                    return null; // Ya existe un servicio con el mismo nombre
                }
            }
            servicioRq.setActivo(true);
            return this.servicioRepository.save(servicioRq);
        }
    }
    

    public void deleteById(Long id){
        Optional<Servicio> service = this.findByIdServicio(id);
        if(service.isPresent()){
            Servicio s = service.get();
            s.setActivo(false);
            Optional.of(this.saveServicio(s));
        } else {
            throw new UnsupportedOperationException("El servicio no se encuentra en la BD.");
        }
    }

    public List<Servicio> filtrarServicio(Servicio servicioRq){
        List<Servicio> serviciosFilter = this.findAllServicios();

        serviciosFilter = serviciosFilter.stream()
                .filter(s -> s.isActivo() == servicioRq.isActivo())
                .collect(Collectors.toList());

        if(servicioRq.getNombre() == null || servicioRq.getNombre().trim().equalsIgnoreCase("") ){
            System.out.println("No se filtra por nombre");
        } else {
            serviciosFilter = serviciosFilter.stream()
                .filter(s -> s.getNombre() != null && s.getNombre().toLowerCase().contains(servicioRq.getNombre().toLowerCase()))
                .collect(Collectors.toList());
        }

        if(servicioRq.getDescripcion() == null || servicioRq.getDescripcion().trim().equalsIgnoreCase("") ){
            System.out.println("No se filtra por nombre");
        } else {
            serviciosFilter = serviciosFilter.stream()
                .filter(s -> s.getDescripcion() != null && s.getDescripcion().toLowerCase().contains(servicioRq.getDescripcion().toLowerCase()))
                .collect(Collectors.toList());
        }

        return serviciosFilter;
    }

}
