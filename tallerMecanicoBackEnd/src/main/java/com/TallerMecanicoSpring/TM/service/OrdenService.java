package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.model.Orden;
import com.TallerMecanicoSpring.TM.model.Servicio;
import com.TallerMecanicoSpring.TM.model.Tecnico;
import com.TallerMecanicoSpring.TM.repository.OrdenRepository;
import com.TallerMecanicoSpring.TM.repository.TecnicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {
    @Autowired
    OrdenRepository ordenRepository;

    @Autowired
    TecnicoRepository tecnicoRepository;

    public List<Orden> findAllOrdenes() {
        return ordenRepository.findAll();
    }

    public Optional<Orden> findByIdOrden(Long id) {
        return ordenRepository.findById(id);
    }

    @Transactional
    public Orden saveOrden(Orden orden) {
        if (orden.getId() == null) {
            // GUARDAR NUEVA ORDEN
            Date fi = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            orden.setFechaIngreso(format.format(fi));
            return ordenRepository.save(orden);
        } else {
            // ACTUALIZAR ORDEN

            // Buscamos el tecnico en base de datos
            Tecnico nuevoTecnico =  tecnicoRepository.findById(orden.getTecnico().getId()).get();
            // Asignamos el nuevo tecnico
            orden.setTecnico(nuevoTecnico);
            
            return ordenRepository.save(orden);
        }
    }

    public Orden updateOrden(Orden o) {
        return this.saveOrden(o);
    }

    public List<Orden> getByIdCliente(Long id) {
        List<Orden> ordenes = this.findAllOrdenes();
        List<Orden> ordenesRs = new ArrayList<>();

        for (Orden o : ordenes) {
            if (o.getVehiculo().getCliente().getId() == id) {
                ordenesRs.add(o);
            }
        }
        return ordenesRs;
    }

}
