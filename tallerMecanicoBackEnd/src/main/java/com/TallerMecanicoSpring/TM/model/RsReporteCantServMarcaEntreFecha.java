package com.TallerMecanicoSpring.TM.model;

public class RsReporteCantServMarcaEntreFecha {
   private String marca;
   private String nombreServicio;
   private Long cantidad;

   public String getMarca() {
    return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getNombreServicio() {
        return nombreServicio;
    }
    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }
    public Long getCantidad() {
        return cantidad;
    }
    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    } 
}
