package com.example.demo.dtos;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

public class BusquedaArticulosDTO {

    private String nombre;
    private float precioCompra;
    private int stockActual;
    private int stockDeSeguridad;
    private int loteOptimo;
    private float cgiArticulo;
    private Date fechaAlta;

    public BusquedaArticulosDTO(String nombre, float precioCompra, int stockActual, int stockDeSeguridad, int loteOptimo, float cgiArticulo, Date fechaAlta) {
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.stockActual = stockActual;
        this.stockDeSeguridad = stockDeSeguridad;
        this.loteOptimo = loteOptimo;
        this.cgiArticulo = cgiArticulo;
        this.fechaAlta = fechaAlta;
    }

    public BusquedaArticulosDTO() {
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public void setStockDeSeguridad(int stockDeSeguridad) {
        this.stockDeSeguridad = stockDeSeguridad;
    }

    public void setLoteOptimo(int loteOptimo) {
        this.loteOptimo = loteOptimo;
    }

    public void setCgiArticulo(float cgiArticulo) {
        this.cgiArticulo = cgiArticulo;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
}
