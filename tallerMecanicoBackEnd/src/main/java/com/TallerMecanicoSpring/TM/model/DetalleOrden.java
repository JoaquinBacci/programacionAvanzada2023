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
    @ManyToOne()
    @JoinColumn(name="id_servicio")
    private Servicio servicio;


    @ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id")
    private Orden orden;

    

    private int cantidad;

    private double precioIndividual;
    private double precioTotal;
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
        precioTotal = ((this.getPrecioIndividual() + (this.getPrecioIndividual() * servicio.getImpuesto()) / 100)* this.getCantidad());
        return precioTotal;
    }

    //     this.total = this.total + ( servicio.precio + (servicio.precio * servicio.impuesto) / 100 );

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public double getPrecioIndividual() {
        return precioIndividual;
    }

    public void setPrecioIndividual(double precioIndividual) {
        this.precioIndividual = precioIndividual;
    }

    @Override
    public String toString() {
        return "DetalleOrden{" +
                "id=" + id +
                ", servicio=" + servicio +
                ", orden=" + orden +
                ", cantidad=" + cantidad +
                ", precioIndividual=" + precioIndividual +
                ", precioTotal=" + precioTotal +
                '}';
    }
}