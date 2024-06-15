package com.example.demo.dtos;

public class DemandaHistoricaDTO {

    private long id;
   private String nombreArticulo;
    private String fechaInicio;
    private String fechaFin;
    private int cantidadVendida;

    public DemandaHistoricaDTO(long id, int cantidadVendida, String fechaFin, String fechaInicio, String nombreArticulo) {
        this.id = id;
        this.cantidadVendida = cantidadVendida;
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;
        this.nombreArticulo = nombreArticulo;
    }

    public DemandaHistoricaDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }
}
