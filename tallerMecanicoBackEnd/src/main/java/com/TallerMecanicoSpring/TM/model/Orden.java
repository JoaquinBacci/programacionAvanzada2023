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

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    private Date fechaIngreso;

    @Column(name = "estado_actual")
    private String estadoActual; //"creada" "enCurso" "finalizada" "cancelada" "facturada"

    @Column(name = "estado_anterior")
    private String estadoAnterior;

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public void crear(){
        this.setEstadoAnterior(null);
        this.setEstadoActual("creada");
    }

    public void iniciar(){
        this.setEstadoAnterior(this.getEstadoActual());
        this.setEstadoActual("enCurso");
    }

    public void finalizar(){
        this.setEstadoAnterior(this.getEstadoActual());
        this.setEstadoActual("finalizada");
    }

    public void cancelar(){
        this.setEstadoAnterior(this.getEstadoActual());
        this.setEstadoActual("cancelada");
    }

    public void descancelar(){
        this.setEstadoActual(this.getEstadoAnterior());
        this.setEstadoAnterior("cancelada");
    }

    public void facturar(){
        this.setEstadoAnterior(this.getEstadoActual());
        this.setEstadoActual("facturada");
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

    public Orden() {
    }

    public Orden(Long id, boolean activo, List<DetalleOrden> detallesOrden, Tecnico tecnico, Vehiculo vehiculo, String descripcion) {
        this.id = id;
        this.activo = activo;
        this.detallesOrden = detallesOrden;
        this.tecnico = tecnico;
        this.vehiculo = vehiculo;
        this.descripcion = descripcion;
    }


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