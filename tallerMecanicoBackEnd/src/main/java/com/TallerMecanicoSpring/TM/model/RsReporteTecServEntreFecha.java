package com.TallerMecanicoSpring.TM.model;

import java.util.Date;
import java.util.List;

public class RsReporteTecServEntreFecha {
    private Date fechaIngreso;
    private String nombreTecnico;
    private Long idOrden;
    private List<String> nombreServicios;
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

    public List<String> getNombreServicios() {
        return nombreServicios;
    }
    public void setNombreServicios(List<String> nombreServicio) {
        this.nombreServicios = nombreServicio;
    }

    public Long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }

    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
}
