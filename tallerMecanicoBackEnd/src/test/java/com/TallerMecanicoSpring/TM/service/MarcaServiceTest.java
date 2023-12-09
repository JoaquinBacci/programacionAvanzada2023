package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.model.Marca;
import com.TallerMecanicoSpring.TM.repository.MarcaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MarcaServiceTest {
    @Mock
    private MarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;

    private Marca marca;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    void findAll() {
        when(marcaRepository.findAll()).thenReturn(Arrays.asList(marca));
        assertNotNull(marcaService.findAll());
    }

    @Test
    void createMarca() {
        Marca marcaParaGuardar = new Marca();
        marcaParaGuardar.setNombre("Marca Argentina");
        marcaParaGuardar.setActivo(true);

        when(marcaRepository.save(marcaParaGuardar)).thenReturn(marca);

        Marca marcaGuardada = marcaService.save(marcaParaGuardar);

        // Verifica que el método save del repositorio haya sido invocado con el objeto correcto
        verify(marcaRepository).save(marcaParaGuardar);

        // Verifica que el objeto devuelto por el servicio sea el mismo que el esperado
        assertEquals(marca, marcaGuardada);

    }
}