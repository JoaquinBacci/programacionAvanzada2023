package com.TallerMecanicoSpring.TM.dao;
import com.TallerMecanicoSpring.TM.model.DetalleOrden;

import java.util.List;

public class OrdenSaveRq {
    private Long idOrden;
    private Long idTecnico;
    private Long idVehiculo;
    private List<DetalleOrden> detallesAGuardar;
    private List<DetalleOrden> detallesAEliminar;
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public List<DetalleOrden> getDetallesAGuardar() {
        return detallesAGuardar;
    }
    public void setDetallesAGuardar(List<DetalleOrden> detallesAGuardar) {
        this.detallesAGuardar = detallesAGuardar;
    }
    public List<DetalleOrden> getDetallesAEliminar() {
        return detallesAEliminar;
    }
    public void setDetallesAEliminar(List<DetalleOrden> detallesAEliminar) {
        this.detallesAEliminar = detallesAEliminar;
    }
    public Long getIdOrden() {
        return idOrden;
    }
    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }
    public Long getIdTecnico() {
        return idTecnico;
    }
    public void setIdTecnico(Long idTecnico) {
        this.idTecnico = idTecnico;
    }
    public Long getIdVehiculo() {
        return idVehiculo;
    }
    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
    
    
}
