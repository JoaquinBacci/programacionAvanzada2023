package com.TallerMecanicoSpring.TM.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TallerMecanicoSpring.TM.model.Factura;
import com.TallerMecanicoSpring.TM.model.Orden;
import com.TallerMecanicoSpring.TM.repository.FacturaRepository;
import com.TallerMecanicoSpring.TM.repository.OrdenRepository;

@Service
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private OrdenRepository ordenRepository;


    public Factura save(Long idOrden){
        Optional<Factura> f = this.findByIdOrden(idOrden);

        if (!f.isPresent()) { //si la factura no existe en la bs se la genera
            Optional<Orden> ordenExistente = ordenRepository.findById(idOrden);

            if (ordenExistente.isPresent() && "finalizada".equals(ordenExistente.get().getEstadoActual())) {
                Factura factura = new Factura();
                factura.setFecha(new Date());
                factura.setOrden(ordenExistente.get());
                facturaRepository.save(factura);
                return factura;
            } else {
                throw new UnsupportedOperationException("No se puede guardar la factura");
            }
        } else{
            return f.get();
        }
    }

    public Optional<Factura> findByIdOrden(Long idOrden){
        List<Factura> facturas = this.facturaRepository.findAll();

        return facturas.stream()
            .filter(factura -> idOrden.equals(factura.getOrden().getId()))
            .findFirst();
    }
}