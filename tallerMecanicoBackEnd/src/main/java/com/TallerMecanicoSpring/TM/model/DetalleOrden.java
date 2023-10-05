package com.TallerMecanicoSpring.TM.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "detalle_orden")
public class DetalleOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "detalleOrden",cascade = CascadeType.ALL)
    private List<Servicio> servicios;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "orden_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Orden orden;

    private int cantidad;

    public DetalleOrden() {
    }

    public DetalleOrden(Long id, List<Servicio> servicios, int cantidad) {
        this.id = id;
        this.servicios = servicios;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }
}
