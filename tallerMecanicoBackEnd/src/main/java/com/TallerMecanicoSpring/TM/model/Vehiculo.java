
package com.TallerMecanicoSpring.TM.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Table(name="vehiculo")
public class Vehiculo {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer kilometraje;
    @NotNull
    private String patente;
    @ManyToOne
    @JoinColumn(name="id_marca")
    @NotNull
    private Marca marca;
    @ManyToOne
    @JoinColumn(name="id_modelo")
    @NotNull
    private Modelo modelo;
    @ManyToOne
    @JoinColumn(name="id_cliente")
    @NotNull
    private Cliente cliente;
    private Boolean activo;

    @OneToMany(mappedBy = "vehiculo",cascade = CascadeType.ALL)
    private List<Orden> ordenes;

    public Vehiculo( ) {
    }

    public Vehiculo(Long id, Integer kilometraje, String patente, Marca marca, Modelo modelo, Cliente cliente, Boolean activo) {
        this.id = id;
        this.kilometraje = kilometraje;
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.cliente = cliente;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    

    public Integer getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public List<Orden> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }
}
