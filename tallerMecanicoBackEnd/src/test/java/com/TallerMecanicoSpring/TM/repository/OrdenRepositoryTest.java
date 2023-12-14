package com.TallerMecanicoSpring.TM.repository;

import com.TallerMecanicoSpring.TM.model.*;
import com.TallerMecanicoSpring.TM.repository.OrdenRepository;
import com.TallerMecanicoSpring.TM.repository.ServicioRepository;
import com.TallerMecanicoSpring.TM.repository.TecnicoRepository;
import com.TallerMecanicoSpring.TM.repository.VehiculoRepository;
import com.TallerMecanicoSpring.TM.service.OrdenService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

    @Test
    @Transactional
    void guardarOrden(){
        orden = new Orden();

        // Buscamos al técnico 1 con ID 1
        Optional<Tecnico> tecnicoExistente = this.tecnicoRepository.findById(1L);

        if (tecnicoExistente.isPresent()) {
            orden.setTecnico(tecnicoExistente.get());
        } else {
            orden.setTecnico(null);
        }
//        Buscamos el vehiculo del cliente con ID 1
        Optional<Vehiculo> vehiculoExistente = this.vehiculoRepository.findById(1L);
        if(vehiculoExistente.isPresent()){
            orden.setVehiculo(vehiculoExistente.get());
        }else{
            orden.setVehiculo(null);
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
        orden.setActivo(true);

        Orden ordenGuardada = ordenRepository.save(orden);

        // Ahora comparamos los resultados reales con los esperados
        assertAll("Multiples Aserciones",
                () -> assertNotNull(orden.getVehiculo(),"El valor no puede ser nulo"),
                () -> assertNotNull(orden.getTecnico(),"El valor no puede ser nulo"),
                () -> assertEquals("Mamani",ordenGuardada.getVehiculo().getCliente().getApellido()),
                () -> assertEquals("Ferradans", ordenGuardada.getTecnico().getApellido())
                );

    }

    @Test
    @Transactional
    void guardarOrdenSinVehiculo(){
        orden = new Orden();
        // Buscamos al técnico que tiene el ID 1
        Optional<Tecnico> tecnicoExistente = this.tecnicoRepository.findById(1L);

        if (tecnicoExistente.isPresent()) {
            orden.setTecnico(tecnicoExistente.get());
        } else {
            orden.setTecnico(null);
        }

        // No proporcionamos un vehículo, dejamos la propiedad vehiculo de la orden como null
        orden.setVehiculo(null);

        // Buscamos los servicios para agregar al detalle
        List<DetalleOrden> detallesOrden = new ArrayList<>();
        Optional<Servicio> servicioExistente = this.servicioRepository.findById(1L);

        // verificamos que el servicio exista
        if (servicioExistente.isPresent()) {
            DetalleOrden detalleOrden = new DetalleOrden();
            detalleOrden.setServicio(servicioExistente.get());
            detalleOrden.setCantidad(2);
            detallesOrden.add(detalleOrden);
        } else {
            System.out.println("Servicio no encontrado");
        }

        orden.setDetallesOrden(detallesOrden);
        orden.setActivo(true);

        // FALTA ASERCIONES
    }
}
