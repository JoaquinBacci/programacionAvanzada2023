package com.TallerMecanicoSpring.TM.model;

import java.util.Date;

public class RqReporteCantServMarcaEntreFecha {
    private Date fechaDesde;
    private Date fechaHasta;
    private long[] idsServicios;
    private long[] idMarcas;
    
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
    public long[] getIdsServicios() {
        return idsServicios;
    }
    public void setIdsServicios(long[] idsServicios) {
        this.idsServicios = idsServicios;
    }
    public long[] getIdMarcas() {
        return idMarcas;
    }
    public void setIdMarcas(long[] idMarcas) {
        this.idMarcas = idMarcas;
    }
     
}