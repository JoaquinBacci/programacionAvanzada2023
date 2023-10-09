package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.model.Orden;
import com.TallerMecanicoSpring.TM.model.Servicio;
import com.TallerMecanicoSpring.TM.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {
    @Autowired
    OrdenRepository ordenRepository;

    public List<Orden> findAllOrdenes(){
        return ordenRepository.findAll();
    }

    public Optional<Orden> findByIdOrden(Long id){
        return ordenRepository.findById(id);
    }


    public Orden saveOrden(Orden orden){
        return ordenRepository.save(orden);
    }

}
