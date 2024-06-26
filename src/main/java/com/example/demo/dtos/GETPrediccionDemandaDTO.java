package com.example.demo.dtos;

public class GETPrediccionDemandaDTO {

    Long id;

    int porcentajeDeError;

    String fechaPedido;

    String fechaInicio;

    String fechaFin;

    String cantidadPeriodo;

    String metodoCalculoError;

    String metodoPrediccion;

    String fijacionErrorAceptable;

    public GETPrediccionDemandaDTO(String cantidadPeriodo, String fechaFin, String fechaInicio, String fechaPedido, String fijacionErrorAceptable, Long id, String metodoCalculoError, String metodoPrediccion, int porcentajeDeError) {
        this.cantidadPeriodo = cantidadPeriodo;
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;
        this.fechaPedido = fechaPedido;
        this.fijacionErrorAceptable = fijacionErrorAceptable;
        this.id = id;
        this.metodoCalculoError = metodoCalculoError;
        this.metodoPrediccion = metodoPrediccion;
        this.porcentajeDeError = porcentajeDeError;
    }

    public GETPrediccionDemandaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCantidadPeriodo() {
        return cantidadPeriodo;
    }

    public void setCantidadPeriodo(String cantidadPeriodo) {
        this.cantidadPeriodo = cantidadPeriodo;
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

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getFijacionErrorAceptable() {
        return fijacionErrorAceptable;
    }

    public void setFijacionErrorAceptable(String fijacionErrorAceptable) {
        this.fijacionErrorAceptable = fijacionErrorAceptable;
    }

    public String getMetodoCalculoError() {
        return metodoCalculoError;
    }

    public void setMetodoCalculoError(String metodoCalculoError) {
        this.metodoCalculoError = metodoCalculoError;
    }

    public String getMetodoPrediccion() {
        return metodoPrediccion;
    }

    public void setMetodoPrediccion(String metodoPrediccion) {
        this.metodoPrediccion = metodoPrediccion;
    }

    public int getPorcentajeDeError() {
        return porcentajeDeError;
    }

    public void setPorcentajeDeError(int porcentajeDeError) {
        this.porcentajeDeError = porcentajeDeError;
    }
}
