package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.dto.RqReporteDTO;
import com.TallerMecanicoSpring.TM.model.DetalleOrden;
import com.TallerMecanicoSpring.TM.model.Orden;
import com.TallerMecanicoSpring.TM.model.RqReporteTecServEntreFecha;
import com.TallerMecanicoSpring.TM.model.RsReporteTecServEntreFecha;
import com.TallerMecanicoSpring.TM.model.Servicio;
import com.TallerMecanicoSpring.TM.service.OrdenService;
import com.TallerMecanicoSpring.TM.repository.OrdenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReporteOrdenService {

    @Autowired
    private OrdenService ordenServicio;

    public List<RsReporteTecServEntreFecha> reporteOrdenesPeriodo(RqReporteDTO rqdto){
        
        List<Orden> allOrdenes = ordenServicio.findAllOrdenes();
        List<RsReporteTecServEntreFecha> listRs = new ArrayList<RsReporteTecServEntreFecha>();

        RqReporteTecServEntreFecha rq = new RqReporteTecServEntreFecha();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            System.out.println("fecha desde antes de format " + rqdto.getFechaDesde());
            // Convertir las fechas desde String a Date
            rq.setFechaDesde(dateFormat.parse(rqdto.getFechaDesde()));
            System.out.println("fecha desde despues de format " + dateFormat.parse(rqdto.getFechaHasta()));
            rq.setFechaHasta(dateFormat.parse(rqdto.getFechaHasta()));
            rq.setIdsTecnicos(rqdto.getIdsTecnicos());
            rq.setIdsServicios(rqdto.getIdsServicios());
        } catch (ParseException e) {
            // Manejar la excepción si hay un problema al parsear las fechas
            e.printStackTrace();
        }


                
        Date fechaDesde = rq.getFechaDesde();
        System.out.println(fechaDesde);
        Date fechaHasta = rq.getFechaHasta();
        Long[] idsTecnicos = rq.getIdsTecnicos();
        Long[] idsServicios = rq.getIdsServicios();

        allOrdenes = allOrdenes.stream()
                .filter(o -> o.getFechaIngreso() != null && fechaDesde != null && fechaHasta != null &&
                        o.getFechaIngreso().after(fechaDesde) && o.getFechaIngreso().before(fechaHasta))
                .collect(Collectors.toList());
        System.out.println(allOrdenes);
        for(Orden o: allOrdenes){
            for(Long i: idsTecnicos){
                if(o.getTecnico().getId() == i){
                    RsReporteTecServEntreFecha rs = new RsReporteTecServEntreFecha();
                    boolean servicioExistente = false;
                    List<String> nombresServicios = new ArrayList<>();
                    double montoTotal = 0;
                    for(Long j: idsServicios){
                        for(DetalleOrden detalle: o.getDetallesOrden()){
                            if(detalle.getServicio().getId() == j){
                                servicioExistente=true;
                                nombresServicios.add(detalle.getServicio().getNombre());
                                System.out.println(detalle.getServicio().getNombre());
                                montoTotal = montoTotal + detalle.getPrecioTotal();
                            }
                        }
                    }
                    if(servicioExistente){
                        rs.setFechaIngreso(o.getFechaIngreso());
                        rs.setNombreTecnico(o.getTecnico().getNombre());
                        rs.setNombreServicios(nombresServicios);
                        rs.setMonto(montoTotal);
                        rs.setIdOrden(o.getId());
                        listRs.add(rs);
                    }
                }
            }
        } 
        
        return listRs;
    }
    
}
