
package com.TallerMecanicoSpring.TM.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="modelo")
public class Modelo {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "por favor ingrese un nombre")
    private String nombre;
    @AssertTrue(message = "La marca debe ser un booleano")
    private boolean activo;
    @ManyToOne
    @JoinColumn(name="id_marca")
    private Marca marca;

    public Modelo() {
    }

    public Modelo(Long id, String nombre, boolean activo, Marca marca) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
        this.marca = marca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
    
    
}
