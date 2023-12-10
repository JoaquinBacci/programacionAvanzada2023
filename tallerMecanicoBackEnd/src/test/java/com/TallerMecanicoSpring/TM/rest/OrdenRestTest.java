package com.TallerMecanicoSpring.TM.rest;

import com.TallerMecanicoSpring.TM.model.*;
import com.TallerMecanicoSpring.TM.repository.OrdenRepository;
import com.TallerMecanicoSpring.TM.repository.ServicioRepository;
import com.TallerMecanicoSpring.TM.repository.TecnicoRepository;
import com.TallerMecanicoSpring.TM.repository.VehiculoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrdenRestTest {


    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    private Orden orden;

    @Test
    @Transactional
    void guardarOrden(){
        orden = new Orden();
        // Buscamos al técnico 1 que seria Mateo
        Optional<Tecnico> tecnicoExistente = this.tecnicoRepository.findById(1L);

        // Verificamos que el tecnico existe
        if(tecnicoExistente.isPresent()){
            orden.setTecnico(tecnicoExistente.get());
            System.out.println("Nombre Tecnico" + orden.getTecnico().getNombre());
            System.out.println("Apellido Tecnico" + orden.getTecnico().getApellido());
        }else{
            System.out.println("No se encontro tecnico");
        }

//        Buscamos el vehiculo de Nicolas
        Optional<Vehiculo> vehiculoExistente = this.vehiculoRepository.findById(1L);
        // verificamos que el vehiculo exista
        if(vehiculoExistente.isPresent()){
            orden.setVehiculo(vehiculoExistente.get());
            System.out.println(vehiculoExistente.get().getCliente().getNombre());
            System.out.println(vehiculoExistente.get().getCliente().getApellido());
        }else {
            System.out.println("Tecnico no encontrado");
        }

        // Buscamos los servicios para agregar al detalle
        List<DetalleOrden> detallesOrden = new ArrayList<>();
        Optional<Servicio> servicioExistente = this.servicioRepository.findById(1L);
        // verificamos que el servicio exista
        if(servicioExistente.isPresent()){
            DetalleOrden detalleOrden = new DetalleOrden();
            detalleOrden.setServicio(servicioExistente.get());
            detalleOrden.setCantidad(2);
            detallesOrden.add(detalleOrden);
        }else {
            System.out.println("Servicio no encontrado");
        }
        orden.setDetallesOrden(detallesOrden);

        System.out.println(detallesOrden.get(0).getServicio());
        orden.setActivo(true);
        Orden ordenGuardada = ordenRepository.save(orden);

        // Ahora comparamos los resultados reales con los esperados
        assertAll("Multiples Aserciones",
                () -> assertEquals("Mamani",ordenGuardada.getVehiculo().getCliente().getNombre()),
                () -> assertEquals("Ferradans", ordenGuardada.getTecnico().getNombre())
                );

    }

    @Test
    @Transactional
    void guardarOrdenSinVehiculo(){
        // Falta implementar
    }
}
