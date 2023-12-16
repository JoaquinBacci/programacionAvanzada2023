package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.model.DetalleOrden;
import com.TallerMecanicoSpring.TM.model.Servicio;
import com.TallerMecanicoSpring.TM.repository.DetalleOrdenRepository;
import com.TallerMecanicoSpring.TM.repository.ServicioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleOrdenService {
    @Autowired
    DetalleOrdenRepository detalleOrdenRepository;

    @Autowired
    ServicioRepository servicioRepository;

    public List<DetalleOrden> findAllDetallesOrden(){
        return detalleOrdenRepository.findAll();
    }

    public Optional<DetalleOrden> findById(Long id){
        return detalleOrdenRepository.findById(id);
    }

    public DetalleOrden saveDetalleOrden(DetalleOrden detalleOrden){

        Optional<Servicio> servicioExistente = servicioRepository.findById(detalleOrden.getServicio().getId());
            if (servicioExistente.isPresent()) {
                // Si se encuentra el servicio existente, asignarlo al detalle de la orden
                detalleOrden.setServicio(servicioExistente.get());
                // Asignamos el precio de descuento que pone el tecnico o dejamos el precio del servicio comun
                // el front-end tiene que mandar 0 si el tecnico no modifica precio y si si modifica tiene que mandar el precio modificado
                if(detalleOrden.getPrecioIndividual() == 0) {
                    detalleOrden.setPrecioIndividual(servicioExistente.get().getPrecio());
                }
                System.out.println("Precio individual" + detalleOrden.getPrecioIndividual());
                System.out.println("Precio total" + (detalleOrden.getPrecioIndividual()  * detalleOrden.getCantidad()));

                detalleOrden.setPrecioTotal(detalleOrden.getPrecioIndividual()  * detalleOrden.getCantidad());

                // Agregar el detalle de la orden a la lista
                //detallesOrden.add(detalleOrden);
                this.detalleOrdenRepository.save(detalleOrden);
            } else {
                throw new UnsupportedOperationException("Servicio no existente en base de datos");
                // Manejar el caso en el que el servicio no existe en la base de datos
                // Puedes lanzar una excepción, devolver un error, o manejarlo de otra manera según tus requerimientos.
            }



        return detalleOrdenRepository.save(detalleOrden);
    }
}
