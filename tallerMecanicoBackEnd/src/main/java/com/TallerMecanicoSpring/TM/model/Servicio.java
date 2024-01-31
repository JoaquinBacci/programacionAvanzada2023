package com.TallerMecanicoSpring.TM.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "por favor ingrese un nombre")
    private String nombre;

    @NotNull //para que no se ingresen nulos
    @DecimalMin(value = "0.00") //el precio debe ser mayor a este nro
    @DecimalMax(value = "1000000.0") //el precio no puede ser mayor a este nro
    private double precio;

    @NotNull(message = "La marca debe ser un booleano")
    private boolean activo;


    private String descripcion;

    /* @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "detalle_orden_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public DetalleOrden detalleOrden; */


    public Servicio() {
    }

    public Servicio(Long id, String nombre, double precio, boolean activo, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
