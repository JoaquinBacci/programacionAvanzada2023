package com.TallerMecanicoSpring.TM.dto;

import java.util.Date;

public class RqReporteDTO {
    private String fechaDesde;
    private String fechaHasta;
    private Long[] idsTecnicos;
    private Long[] idsServicios;

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
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
