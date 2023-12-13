package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.model.DetalleOrden;
import com.TallerMecanicoSpring.TM.model.Orden;
import com.TallerMecanicoSpring.TM.model.RqReporteTecServEntreFecha;
import com.TallerMecanicoSpring.TM.model.RsReporteTecServEntreFecha;
import com.TallerMecanicoSpring.TM.model.Servicio;
import com.TallerMecanicoSpring.TM.repository.OrdenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    

    @Transactional
    public Orden saveOrden(Orden orden){
        if(orden.getId() == null){
            Date fi = new Date();
            //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            //Recordar Reparar
            orden.setFechaIngreso(fi);
            return ordenRepository.save(orden);
        } else {

            Optional<Orden> ordenExistente = this.findByIdOrden(orden.getId());

            if(ordenExistente.isPresent()){
                Orden o = ordenExistente.get();

                o.setActivo(orden.isActivo());
                o.setDescripcion(orden.getDescripcion());
                o.setDetallesOrden(orden.getDetallesOrden());
                o.setVehiculo(orden.getVehiculo());
                o.setTecnico(orden.getTecnico());
                return ordenRepository.save(o);
            } else{
                return null;
            }


            
        }
        
    }

    public Orden updateOrden(Orden o){
        return this.saveOrden(o);
    }

    public List<Orden> getByIdCliente(Long id){
        List<Orden> ordenes = this.findAllOrdenes();
        List<Orden> ordenesRs = new ArrayList<>();

        for( Orden o : ordenes){
            if(o.getVehiculo().getCliente().getId() == id){
                ordenesRs.add(o);
            }
        }
        return ordenesRs;
    }

}
