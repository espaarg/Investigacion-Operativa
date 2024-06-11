package com.example.demo.dtos;

public class VentaDTO {
    private Long id;
    private Long montoTotal;
    private String fecha;

    public VentaDTO(String detalle, String fecha, Long id, Long montoTotal) {

        this.fecha = fecha;
        this.id = id;
        this.montoTotal = montoTotal;
    }

    public VentaDTO() {
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Long montoTotal) {
        this.montoTotal = montoTotal;
    }
}
