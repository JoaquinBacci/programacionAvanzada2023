 package com.TallerMecanicoSpring.TM.model;

/* import com.fasterxml.jackson.annotation.JsonProperty; */
import jakarta.persistence.*;

/* import java.util.List; */

@Entity
@Table(name = "detalle_orden")
public class DetalleOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_servicio")
    private Servicio servicio;

    private double precioTotal;

    /* @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "orden_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Orden orden; */

    @ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id")
    private Orden orden;

    private int cantidad;

    public DetalleOrden() {
    }

    public DetalleOrden(Long id, Servicio servicio, int cantidad) {
        this.id = id;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.precioTotal = getPrecioTotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioTotal() {
        precioTotal = (this.servicio.getPrecio() * this.getCantidad());
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }


  /* public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    } */
}