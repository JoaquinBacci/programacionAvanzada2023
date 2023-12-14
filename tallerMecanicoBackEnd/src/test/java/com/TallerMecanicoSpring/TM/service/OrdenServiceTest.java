package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.model.DetalleOrden;
import com.TallerMecanicoSpring.TM.model.Orden;
import com.TallerMecanicoSpring.TM.model.Servicio;
import com.TallerMecanicoSpring.TM.model.Tecnico;
import com.TallerMecanicoSpring.TM.repository.OrdenRepository;
import com.TallerMecanicoSpring.TM.repository.ServicioRepository;
import com.TallerMecanicoSpring.TM.repository.TecnicoRepository;
import com.TallerMecanicoSpring.TM.repository.VehiculoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
public class OrdenServiceTest {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private OrdenService ordenService;

    private Orden orden;

    @Test
    @Transactional
    void guardarOrdenSinVehiculo(){
    //Falta implementar
    }
}
