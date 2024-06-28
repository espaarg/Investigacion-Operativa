package com.example.demo.dtos;

public class GETPrediccionDemandaDTO {

    Long id;

    int porcentajeDeError;

    String fechaInicio;

    double error;

    String fechaFin;

    String cantidadPeriodo;

    String metodoPrediccion;

    String articulo;

    double valorPrediccion;

    public GETPrediccionDemandaDTO(String articulo, String cantidadPeriodo,double error, String fechaFin, String fechaInicio, String fechaPedido, String fijacionErrorAceptable, Long id, String metodoCalculoError, String metodoPrediccion, int porcentajeDeError, double valorPrediccion) {
        this.articulo = articulo;
        this.cantidadPeriodo = cantidadPeriodo;
        this.error = error;
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;
        this.id = id;
        this.metodoPrediccion = metodoPrediccion;
        this.porcentajeDeError = porcentajeDeError;
        this.valorPrediccion = valorPrediccion;
    }

    public GETPrediccionDemandaDTO() {
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public double getValorPrediccion() {
        return valorPrediccion;
    }

    public void setValorPrediccion(double valorPrediccion) {
        this.valorPrediccion = valorPrediccion;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
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
