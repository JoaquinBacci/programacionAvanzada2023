package com.TallerMecanicoSpring.TM.dto;

import java.util.Date;

public class RqReporteCantDTO {
    private String fechaDesde;
    private String fechaHasta;
    private Long[] idsMarcas;
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

    public Long[] getIdsMarcas() {
        return idsMarcas;
    }

    public void setIdsTecnicos(Long[] idsMarcas) {
        this.idsMarcas = idsMarcas;
    }

    public Long[] getIdsServicios() {
        return idsServicios;
    }

    public void setIdsServicios(Long[] idsServicios) {
        this.idsServicios = idsServicios;
    }
}
