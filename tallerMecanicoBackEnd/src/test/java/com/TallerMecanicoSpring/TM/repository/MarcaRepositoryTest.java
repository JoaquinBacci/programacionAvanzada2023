package com.TallerMecanicoSpring.TM.repository;

import com.TallerMecanicoSpring.TM.model.Marca;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.Assert;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MarcaRepositoryTest {
    @Autowired
    private MarcaRepository marcaRepository;

    @Test
    @DisplayName("Test para guardar la marca de un auto")
    void testGuardarEmpleado() {

        //Vamos a trabajar con metodologia BDD ->
        //given - Dado o condición previa o configuración
        Marca marca1 = new Marca();
        marca1.setNombre("Marca Nueva Testing");
        marca1.setActivo(true);

        //when - acción o comportamiento que vamos a probar
        Marca marcaGuardada = marcaRepository.save(marca1);
        //then - verificar la salida
        assertThat(marcaGuardada).isNotNull(); //verificamos que la marca guardada no sea null
        assertThat(marcaGuardada.getId()).isGreaterThan(0); //verificamos que el id sea mayor a cero



    }
}
