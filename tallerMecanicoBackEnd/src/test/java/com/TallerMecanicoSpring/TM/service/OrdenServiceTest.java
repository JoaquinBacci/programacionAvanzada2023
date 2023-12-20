package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.dao.OrdenSaveRq;
import com.TallerMecanicoSpring.TM.model.*;
import com.TallerMecanicoSpring.TM.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OrdenServiceTest {
    @Mock
    private OrdenRepository ordenRepositoryMock;

    @Mock
    private TecnicoRepository tecnicoRepositoryMock;

    @Mock
    private VehiculoService vehiculoServiceMock;

    @Mock
    private DetalleOrdenService detalleOrdenServiceMock;

    @InjectMocks
    private OrdenService ordenService;

    private Orden orden;

    private OrdenSaveRq ordenRq;

    private Tecnico tecnico;

    private Vehiculo vehiculo;
    private DetalleOrden detalleOrden1;
    private DetalleOrden detalleOrden2;



    @BeforeEach
    void setup(){
        this.orden = new Orden();
        // Creamos un mock de vehiculo
        this.vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setCliente(new Cliente(1L,42567231,"3423544578","Nicolas","Mamani","Bell Ville 123","nico@gmail.com",true));
        vehiculo.setActivo(true);
        vehiculo.setKilometraje(50);
        Marca marca = new Marca(1L,"Ford",true);
        Modelo modelo = new Modelo(1L,"Modelo A",true,marca);
        vehiculo.setMarca(marca);
        vehiculo.setModelo(modelo);
        orden.setVehiculo(vehiculo);
        // Creamos un mock de tecnico
        this.tecnico = new Tecnico(1L,45567876,"3377564532",15678,"Hernan","Arias","Deheza 123","hernan@gmail.com",true);
        orden.setTecnico(tecnico);
        List<DetalleOrden> detallesOrden = new ArrayList<>();
        // creamos un detalle de servicio
        this.detalleOrden1 = new DetalleOrden();
        Servicio servicio1 = new Servicio(1L,"Servicio 1",50,true,"descripción servicio 1");
        detalleOrden1.setServicio(servicio1);
        detalleOrden1.setCantidad(2);
        detalleOrden1.setPrecioTotal(detalleOrden1.getPrecioTotal());
        detallesOrden.add(detalleOrden1);
        // creamos otro detalle de servicio
        this.detalleOrden2 = new DetalleOrden();
        //Servicio servicio2 = servicioRepository.findById(2L).get();
        Servicio servicio2 = new Servicio(2L, "Servicios 2", 80,true,"Descripción servicio 2");
        detalleOrden2.setServicio(servicio2);
        detalleOrden2.setCantidad(1);
        detalleOrden2.setPrecioTotal(detalleOrden2.getPrecioTotal());
        detallesOrden.add(detalleOrden2);
        orden.setActivo(true);
        orden.setDescripcion("Descripción de orden 1");
        orden.crear();
        Date fi = new Date();
        orden.setFechaIngreso(fi);

        // seteamos
        ordenRq = new OrdenSaveRq();
        ordenRq.setIdTecnico(1L);
        ordenRq.setIdVehiculo(1L);
        ordenRq.setDetallesAGuardar(detallesOrden);
        ordenRq.setDescripcion("Descripción de orden 1");
    }

    @Test
    @DisplayName("Test para guardar una orden del service")
    void guardarOrden(){
        given(tecnicoRepositoryMock.findById(this.tecnico.getId())).willReturn(Optional.of(this.tecnico));
        given(vehiculoServiceMock.findById(this.vehiculo.getId())).willReturn(Optional.of(orden.getVehiculo()));
        given(detalleOrdenServiceMock.saveDetalleOrden(detalleOrden1)).willReturn(detalleOrden1);
        given(detalleOrdenServiceMock.saveDetalleOrden(detalleOrden2)).willReturn(detalleOrden2);
        given(ordenRepositoryMock.save(any(Orden.class))).willAnswer(invocation -> invocation.getArgument(0));
        Orden ordenGuardada = ordenService.saveOrden(ordenRq);
        assertThat(ordenGuardada).isNotNull();
    }

    @Test
    @DisplayName("Test para no guardar una orden cuando no tiene tecnico")
    void noGuardarOrdenSinTecnico(){
        ordenRq.setIdTecnico(null);
        assertThrows(UnsupportedOperationException.class, () -> {
            ordenService.saveOrden(ordenRq);
        });
    }

    @Test
    void noGuardarOrdenSinVehiculo(){
        ordenRq.setIdVehiculo(null);
        given(tecnicoRepositoryMock.findById(this.tecnico.getId())).willReturn(Optional.of(this.tecnico));
        assertThrows(UnsupportedOperationException.class, () -> {
            ordenService.saveOrden(ordenRq);
        });
    }
}
