package com.TallerMecanicoSpring.TM.repository;

import com.TallerMecanicoSpring.TM.model.Tecnico;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TecnicoRepositoryTest {
    @Autowired
    private TecnicoRepository tecnicoRepository;

    private Tecnico tecnico;

    @BeforeEach
    void setup(){
        tecnico = new Tecnico();
        tecnico.setNombre("Martin");
        tecnico.setApellido("Coser");
        tecnico.setDni(41234565);
        tecnico.setActivo(true);
        tecnico.setLegajo(14567);
        tecnico.setEmail("martin_coser@gmail.com");
        tecnico.setDireccion("perdices 123");
        tecnico.setNum_tel("3535642321");
    }

    @Test
    @DisplayName("Test para guardar un técnico")
    void testGuardarTecnico(){
        // When
        Tecnico tecnicoGuardado=tecnicoRepository.save(tecnico);
        // Then
        assertThat(tecnicoGuardado).isNotNull(); //verificamos que el tecnico guardado no sea null
        assertThat(tecnicoGuardado.getId()).isGreaterThan(0); //verificamos que el id sea mayor a cero
    }

    @Test
    @DisplayName("Test para no guardar un técnico sin DNI")
    void testNoGuardarTecnicoSinDni(){
        tecnico.setDni(null);
        // Intenta guardar el técnico sin DNI y verifica que se lance la excepción
        assertThrows(ConstraintViolationException.class, () -> {
            tecnicoRepository.save(tecnico);
        });
    }

    @Test
    @DisplayName("Test para no guardar un técnico sin nombre")
    void testNoGuardarTecnicoSinNombre(){
        tecnico.setNombre(null);
        // Intenta guardar el técnico sin DNI y verifica que se lance la excepción
        assertThrows(ConstraintViolationException.class, () -> {
            tecnicoRepository.save(tecnico);
        });
    }

}
