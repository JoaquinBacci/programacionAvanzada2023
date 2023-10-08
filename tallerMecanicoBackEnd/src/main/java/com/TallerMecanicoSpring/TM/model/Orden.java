package com.TallerMecanicoSpring.TM.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;

import java.util.List;

@Entity
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AssertTrue(message = "La marca debe ser un booleano")
    private boolean activo;

    @OneToMany(/* mappedBy = "orden" ,*/cascade = CascadeType.ALL)
    private List<DetalleOrden> detallesOrden;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "tecnico_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Tecnico tecnico;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "vehiculo_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Vehiculo vehiculo;

    /* @JsonIgnore
    private double precioTotal; */

    public Orden() {
    }

    public Orden(Long id, boolean activo, List<DetalleOrden> detallesOrden, Tecnico tecnico, Vehiculo vehiculo) {
        this.id = id;
        this.activo = activo;
        this.detallesOrden = detallesOrden;
        this.tecnico = tecnico;
        this.vehiculo = vehiculo;
        /* this.setPrecioTotal(); */
    }

    /* public double getPrecioTotal() {
        double total = 0.0;
        if (detallesOrden != null) {
            for (DetalleOrden detalle : detallesOrden) {
                total += detalle.getPrecioTotal();
            }
        }
        return total;
    }

    public void setPrecioTotal() {
        double total = 0.0;
        if (detallesOrden != null) {
            for (DetalleOrden detalle : detallesOrden) {
                total += detalle.getPrecioTotal();
            }
        }
        this.precioTotal = total;
    } */

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