package com.TallerMecanicoSpring.TM.model;

import java.util.Date;

public class RsReporteTecServEntreFecha {
    private Date fechaIngreso;
    private String nombreTecnico;
    private String nombreServicio;
    private double monto;

    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public String getNombreTecnico() {
        return nombreTecnico;
    }
    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }
    public String getNombreServicio() {
        return nombreServicio;
    }
    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }
    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
}