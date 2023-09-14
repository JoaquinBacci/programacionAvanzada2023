
package com.TallerMecanicoSpring.TM.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name="vehiculo")
public class Vehiculo {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int kilometraje;
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

    public Vehiculo( ) {
    }

    public Vehiculo(Long id, int kilometraje, String patente, Marca marca, Modelo modelo, Cliente cliente, Boolean activo) {
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
    
    

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
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
}
