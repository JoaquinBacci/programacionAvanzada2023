package com.TallerMecanicoSpring.TM.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.TallerMecanicoSpring.TM.exceptions.ResourceNotFoundException;
import com.TallerMecanicoSpring.TM.model.Tecnico;
import com.TallerMecanicoSpring.TM.repository.TecnicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
        //given
         given(tecnicoRepository.save(tecnico)).willReturn(tecnico); // si no existe el mail, se devuelve el tecnico
        //when
        Tecnico tecnicoGuardado = tecnicoService.save(tecnico);
        //then
        assertThat(tecnicoGuardado).isNotNull();
    }

//    @DisplayName("Prueba Unitaria para guardar tecnico con throw exception")
//    @Test
//    void testGuardarTecnicoConThrow() {
//        //given
//        given(tecnicoRepository.save(tecnico)).willReturn(Optional.of(tecnico)); // si no existe el mail, se devuelve el tecnico
//        //when
//        assertThrows(ResourceNotFoundException.class,() -> {
//            tecnicoService.save(tecnico);
//        });
//        //then
//        verify(tecnicoRepository, never()).save(any(Tecnico.class));
//    }
}
