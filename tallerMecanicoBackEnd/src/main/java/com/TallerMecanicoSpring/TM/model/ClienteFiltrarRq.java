package com.TallerMecanicoSpring.TM.model;

import java.util.Date;

public class ClienteFiltrarRq extends Cliente{
    private String fechaDesde;
    private String fechaHasta;
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

   
}
