
package com.TallerMecanicoSpring.TM.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name="marca")
public class Marca {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY) 
    private Long id;
    @NotBlank(message = "por favor ingrese un nombre")
    private String nombre;
    @AssertTrue(message = "La marca debe ser un booleano")
    private boolean activo;

    public Marca() {
    }

    public Marca(Long id, String nombre, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
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
    
    
}
