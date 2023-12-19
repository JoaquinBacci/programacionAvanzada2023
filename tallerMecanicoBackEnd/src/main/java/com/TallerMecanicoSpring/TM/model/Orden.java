package com.TallerMecanicoSpring.TM.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AssertTrue(message = "Activo debe ser un booleano")
    private boolean activo;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<DetalleOrden> detallesOrden;

    @ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    private Date fechaIngreso;
    private String estado; //"creada" "enCurso" "finalizada" "cancelada"

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void crear(){
        this.setEstado("creada");
    }

    public void iniciar(){
        this.setEstado("enCurso");
    }

    public void finalizar(){
        this.setEstado("finalizada");
    }

    public void cancelar(){
        this.setEstado("cancelada");
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private String descripcion;


//    private double precioTotal;

    public Orden() {
    }

    public Orden(Long id, boolean activo, List<DetalleOrden> detallesOrden, Tecnico tecnico, Vehiculo vehiculo, String descripcion) {
        this.id = id;
        this.activo = activo;
        this.detallesOrden = detallesOrden;
        this.tecnico = tecnico;
        this.vehiculo = vehiculo;
        this.descripcion = descripcion;
        /* this.setPrecioTotal(); */
        /* this.setFechaIngreso(); */
    }

//    public double getPrecioTotal() {
//        double total = 0.0;
//        if (detallesOrden != null) {
//            for (DetalleOrden detalle : detallesOrden) {
//                total += detalle.getPrecioTotal();
//            }
//        }
//        return total;
//    }
//
//    public void setPrecioTotal() {
//        double total = 0.0;
//        if (detallesOrden != null) {
//            for (DetalleOrden detalle : detallesOrden) {
//                total += detalle.getPrecioTotal();
//            }
//        }
//        this.precioTotal = total;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<DetalleOrden> getDetallesOrden() {
        return detallesOrden;
    }

    public void setDetallesOrden(List<DetalleOrden> detallesOrden) {
        this.detallesOrden = detallesOrden;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    
}