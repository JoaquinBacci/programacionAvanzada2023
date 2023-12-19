package com.TallerMecanicoSpring.TM.model;

import java.util.Date;

public class RqReporteTecServEntreFecha {
    private Date fechaDesde;
    private Date fechaHasta;
    private Long[] idsTecnicos;
    private Long[] idsServicios;


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
    public Long[] getIdsTecnicos() {
        return idsTecnicos;
    }
    public void setIdsTecnicos(Long[] idsTecnicos) {
        this.idsTecnicos = idsTecnicos;
    }
    public Long[] getIdsServicios() {
        return idsServicios;
    }
    public void setIdsServicios(Long[] idsServicios) {
        this.idsServicios = idsServicios;
    }
}

