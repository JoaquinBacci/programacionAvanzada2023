package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.model.RqReporteTecServEntreFecha;
import com.TallerMecanicoSpring.TM.model.RsReporteTecServEntreFecha;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ReporteOrdenServiceTest {

    @Autowired
    private ReporteOrdenService reporteOrdenService;

    private RqReporteTecServEntreFecha rqReporte;


//    @BeforeEach
//    void setup(){
//        rqReporte = new RqReporteTecServEntreFecha();
//
//        // Establecer valores para el objeto
//        rqReporte.setFechaDesde(new Date());  // Fecha actual como ejemplo, puedes usar la que desees
//
//        // Crear una fecha para el 10 de días después de la fecha actual como ejemplo
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.DAY_OF_MONTH, 10);
//        Date fechaHasta = calendar.getTime();
//        rqReporte.setFechaHasta(fechaHasta);
//
//        Long[] idsTecnicos = {1L};  // IDs de técnicos como ejemplo
//        rqReporte.setIdsTecnicos(idsTecnicos);
//
//        Long[] idsServicios = {1L};  // IDs de servicios como ejemplo
//        rqReporte.setIdsServicios(idsServicios);
//    }

//    @Test
//    @DisplayName("Test para el reporte de service")
//    void testReporteOrdenService() {
//        List<RsReporteTecServEntreFecha> listaReporte = reporteOrdenService.reporteOrdenesPeriodo(rqReporte);
//        System.out.println("Lista reporte -> " + listaReporte);
//    }
//
}
