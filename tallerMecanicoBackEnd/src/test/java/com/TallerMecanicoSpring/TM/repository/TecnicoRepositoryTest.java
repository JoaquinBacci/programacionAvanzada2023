package com.TallerMecanicoSpring.TM.repository;

import com.TallerMecanicoSpring.TM.model.Tecnico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TecnicoRepositoryTest {
    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Test
    @DisplayName("Test para guardar un técnico")
    void testGuardarTecnico(){
        // Given
        Tecnico tecnico = new Tecnico();
        tecnico.setNombre("Martin");
        tecnico.setApellido("Coser");
        tecnico.setDni(41234565);
        tecnico.setActivo(true);
        tecnico.setLegajo(14567);
        tecnico.setEmail("martin_coser@gmail.com");
        tecnico.setDireccion("perdices 123");
        tecnico.setNum_tel("3535642321");
        // When
        Tecnico tecnicoGuardado=tecnicoRepository.save(tecnico);
        System.out.println("Nombre del Tecnico : " + tecnicoGuardado.getNombre());
        System.out.println("ID del Tecnico : " + tecnicoGuardado.getId());
        // Then
        assertThat(tecnicoGuardado).isNotNull(); //verificamos que el tecnico guardado no sea null
        assertThat(tecnicoGuardado.getId()).isGreaterThan(0); //verificamos que el id sea mayor a cero
    }
}
