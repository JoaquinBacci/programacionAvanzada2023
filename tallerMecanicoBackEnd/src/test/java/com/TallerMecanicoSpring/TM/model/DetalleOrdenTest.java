package com.TallerMecanicoSpring.TM.model;

import com.TallerMecanicoSpring.TM.service.DetalleOrdenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class DetalleOrdenTest {

    @Autowired
    private DetalleOrdenService detalleOrdenService;


    @Test
    void getPrecioTotal() {
        try {
            DetalleOrden detalleOrdenExistente = detalleOrdenService.findById(10L).get();
            System.out.println("Precio Individual" + detalleOrdenExistente.getPrecioIndividual());
            System.out.println("Precio Total" + detalleOrdenExistente.getPrecioTotal());
        } catch (Exception exception) {
            System.out.println("Ocurrio un error " + exception);
        }


    }
}