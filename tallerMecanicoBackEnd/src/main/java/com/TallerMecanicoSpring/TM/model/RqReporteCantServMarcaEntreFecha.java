package com.TallerMecanicoSpring.TM.model;

import java.util.Date;

public class RqReporteCantServMarcaEntreFecha {
    private Date fechaDesde;
    private Date fechaHasta;
    private Long[] idsServicios;
    private Long[] idMarcas;
    
    public Date getFechaDesde() {
        return fechaDesde;
    }
    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }
    public Date getFechaHasta() {
        return fechaHasta;
    }
    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
    public Long[] getIdsServicios() {
        return idsServicios;
    }
    public void setIdsServicios(Long[] idsServicios) {
        this.idsServicios = idsServicios;
    }
    public Long[] getIdMarcas() {
        return idMarcas;
    }
    public void setIdMarcas(Long[] idMarcas) {
        this.idMarcas = idMarcas;
    }
     
}