package com.TallerMecanicoSpring.TM.service;

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


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ReporteOrdenService {

    @Autowired
    private OrdenService ordenServicio;

    public List<RsReporteTecServEntreFecha> reporteOrenesPeriodo(RqReporteTecServEntreFecha rq){
        
        List<Orden> allOrdenes = ordenServicio.findAllOrdenes();
        List<RsReporteTecServEntreFecha> listRs = new ArrayList<RsReporteTecServEntreFecha>();
                
        Date fechaDesde = rq.getFechaDesde();
        Date fechaHasta = rq.getFechaHasta();
        Long[] idsTecnicos = rq.getIdsTecnicos();
        Long[] idsServicios = rq.getIdsServicios();

        allOrdenes = allOrdenes.stream()
            .filter(o -> o.getFechaIngreso().after(fechaDesde) && o.getFechaIngreso().before(fechaHasta))
            .collect(Collectors.toList());

        for(Orden o: allOrdenes){
            for(Long i: idsTecnicos){
                if(o.getTecnico().getId() == i){
                    for(Long j: idsServicios){
                       for(DetalleOrden detalle: o.getDetallesOrden()){
                            if(detalle.getServicio().getId() == j){
                                RsReporteTecServEntreFecha rs = new RsReporteTecServEntreFecha();    
                                rs.setFechaIngreso(o.getFechaIngreso());
                                rs.setNombreServicio(detalle.getServicio().getNombre());
                                rs.setNombreTecnico(o.getTecnico().getNombre());
                                rs.setMonto(detalle.getPrecioTotal());
                                listRs.add(rs);
                            }
                       }
                    }
                }
            }
        } 
        
        return listRs;
    }
    
}
