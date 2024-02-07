package com.TallerMecanicoSpring.TM.service;

import com.TallerMecanicoSpring.TM.dto.RqReporteCantDTO;
import com.TallerMecanicoSpring.TM.dto.RqReporteDTO;
import com.TallerMecanicoSpring.TM.model.DetalleOrden;
import com.TallerMecanicoSpring.TM.model.Orden;
import com.TallerMecanicoSpring.TM.model.RqReporteCantServMarcaEntreFecha;
import com.TallerMecanicoSpring.TM.model.RsReporteCantServMarcaEntreFecha;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;


@Service
public class ReporteCantServiciosMarca {

    @PersistenceContext
    private EntityManager entityManager;
    
    //@Autowired
    //private OrdenService ordenServicio;

    public List<RsReporteCantServMarcaEntreFecha> ReporteCantServiciosMarca(RqReporteCantDTO rqdto){
        
        //List<Orden> allOrdenes = ordenServicio.findAllOrdenes();
        List<RsReporteCantServMarcaEntreFecha> listRs = new ArrayList<RsReporteCantServMarcaEntreFecha>();

        RqReporteCantServMarcaEntreFecha rq = new RqReporteCantServMarcaEntreFecha();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try{
            // Convertir las fechas desde String a Date
            rq.setFechaDesde(dateFormat.parse(rqdto.getFechaDesde()));
            rq.setFechaHasta(dateFormat.parse(rqdto.getFechaHasta()));
            rq.setIdsServicios(rqdto.getIdsServicios());
            rq.setIdMarcas(rqdto.getIdsMarcas());
        } catch (ParseException e) {
            // Manejar la excepción si hay un problema al parsear las fechas
            e.printStackTrace();
        }
        
        String sql = "SELECT marca.nombre, servicio.nombre, COUNT(servicio.id) " +
             "FROM orden " +
             "INNER JOIN orden_detalles_orden ON (orden.id = orden_detalles_orden.orden_id) " +
             "INNER JOIN detalle_orden ON (orden_detalles_orden.detalles_orden_id = detalle_orden.id) " +
             "INNER JOIN servicio ON (detalle_orden.id_servicio = servicio.id) " +
             "INNER JOIN tecnico ON (orden.tecnico_id = tecnico.id) " +
             "INNER JOIN vehiculo ON (orden.vehiculo_id = vehiculo.id) " +
            //"INNER JOIN modelo ON (vehiculo.id_modelo = modelo.id) " +
             "INNER JOIN marca ON (vehiculo.id_marca = marca.id) " +
             "WHERE marca.id IN :idMarcas AND servicio.id IN :idServicios AND DATE(orden.fecha_ingreso) BETWEEN :fechaDesde AND :fechaHasta " +
             "GROUP BY servicio.nombre, marca.nombre " +
             "ORDER BY marca.nombre";

        List<Long> idMarcasList = Arrays.asList(rq.getIdMarcas());
        List<Long> idServiciosList = Arrays.asList(rq.getIdsServicios());

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("fechaDesde", rq.getFechaDesde());
        query.setParameter("fechaHasta", rq.getFechaHasta());
        query.setParameter("idMarcas", idMarcasList);
        query.setParameter("idServicios", idServiciosList);
        List<Object[]> results = query.getResultList();

        for (Object[] result : results) {
            String marcaNombre = (String) result[0];
            String servicioNombre = (String) result[1];
            Long count = (Long) result[2];
    
            RsReporteCantServMarcaEntreFecha rs = new RsReporteCantServMarcaEntreFecha();
            rs.setMarca(marcaNombre);
            rs.setNombreServicio(servicioNombre);
            rs.setCantidad(count);
            listRs.add(rs);

        }
    return listRs;
    }
}
