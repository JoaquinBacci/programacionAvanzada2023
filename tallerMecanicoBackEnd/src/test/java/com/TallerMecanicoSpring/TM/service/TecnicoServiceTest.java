package com.TallerMecanicoSpring.TM.service;

import static org.mockito.BDDMockito.given;

import com.TallerMecanicoSpring.TM.model.Tecnico;
import com.TallerMecanicoSpring.TM.repository.TecnicoRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TecnicoServiceTest {

    @Mock
    private TecnicoRepository tecnicoRepository;

    @InjectMocks
    private TecnicoService tecnicoService;

    private Tecnico tecnico;

    @BeforeEach
    void setup(){
        tecnico = new Tecnico();
        tecnico.setNombre("Martin");
        tecnico.setApellido("Coser");
        tecnico.setDni(41234565);
        tecnico.setActivo(true);
        tecnico.setLegajo(14567);
        tecnico.setEmail("hernan@gmail.com");
        tecnico.setDireccion("perdices 123");
        tecnico.setNum_tel("3535642321");
    }

    @DisplayName("Prueba Unitaria para guardar tecnico")
    @Test
    void testGuardarTecnico(){
         given(tecnicoRepository.save(tecnico)).willReturn(tecnico);
        Tecnico tecnicoGuardado = tecnicoService.save(tecnico);
        assertThat(tecnicoGuardado).isNotNull();
    }

    @DisplayName("Prueba Unitaria para guardar técnico sin DNI")
    @Test
    void testGuardarTecnicoSinDNI() {
        tecnico.setDni(null);
        given(tecnicoRepository.save(tecnico)).willThrow(ConstraintViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> {
            tecnicoService.save(tecnico);
        });
    }
}
