package com.TallerMecanicoSpring.TM.repository;

import com.TallerMecanicoSpring.TM.model.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrdenRepositoryTest {


    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private OrdenRepository ordenRepository;


    private Orden orden;

    @BeforeEach
    void setup(){
        this.orden = new Orden();
        // seteamos datos a la orden
        orden.setTecnico(tecnicoRepository.findById(1L).get());
        orden.setVehiculo(vehiculoRepository.findById(1L).get());
        orden.setActivo(true);
        orden.setDescripcion("Esta orden es el auto Fiesta con patente ABC123");
        // creamos detalle
        DetalleOrden detalleOrden1 = new DetalleOrden();
        DetalleOrden detalleOrden2 = new DetalleOrden();
        detalleOrden1.setServicio(servicioRepository.findById(1L).get());
        detalleOrden2.setServicio(servicioRepository.findById(2L).get());
        List<DetalleOrden> detallesOrden = new ArrayList<>();
        detallesOrden.add(detalleOrden1);
        detallesOrden.add(detalleOrden2);
        // asignamos los detalles a la orden
        orden.setDetallesOrden(detallesOrden);
        orden.crear(); // asignamos estado creada a orden
        Date fi = new Date(); //creamos una fecha para la orden
        orden.setFechaIngreso(fi);
    }

    @Test
    @DisplayName("Test para guardar una orden")
    void testGuardarOrden(){
        Orden ordenGuardada = ordenRepository.save(this.orden);
        // Los resultados esperados seria que no sea nulo el objeto guardado y que el id sea mayor a cero
        assertThat(ordenGuardada).isNotNull();
        assertThat(ordenGuardada.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test con Throws al querer guardar una orden sin tecnico")
    void testNoGuardarOrdenSinTecnico(){
        this.orden.setTecnico(null);
        assertThrows(DataIntegrityViolationException.class, () -> {
            Orden ordenGuardada = ordenRepository.save(this.orden);
        });
    }
    @Test
    @DisplayName("Test con Throws al querer guardar una orden sin vehiculo")
    void testNoGuardarOrdenSinVehiculo(){
        this.orden.setVehiculo(null);
        assertThrows(DataIntegrityViolationException.class, () -> {
            Orden ordenGuardada = ordenRepository.save(this.orden);
        });
    }

}
