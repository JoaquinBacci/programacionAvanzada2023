package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.model.DetalleOrden;
import com.TallerMecanicoSpring.TM.model.Servicio;
import com.TallerMecanicoSpring.TM.repository.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleOrdenService {
    @Autowired
    DetalleOrdenRepository detalleOrdenRepository;

    public List<DetalleOrden> findAllDetallesOrden(){
        return detalleOrdenRepository.findAll();
    }

    public Optional<DetalleOrden> findById(Long id){
        return detalleOrdenRepository.findById(id);
    }

    public DetalleOrden saveDetalleOrden(DetalleOrden detalleOrden){
        System.out.println("PrecioTotal: " + detalleOrden.getPrecioTotal());
        return detalleOrdenRepository.save(detalleOrden);
    }
}
