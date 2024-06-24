package com.example.demo.dtos;

public class OrdenDeCompraDTO {
    private long id;
    private float totalCompra;
    private int cantidad;
    private float precioIndividual;
    private String fechaPedido;
    private String fechaLlegada;
    private String estadoOrdenDeCompra;
    private String proveedorArticulo;
    private String nombreArticulo;

    public OrdenDeCompraDTO(String nombreArticulo, int cantidad, String estadoOrdenDeCompra, String fechaLlegada, String fechaPedido, long id, float precioIndividual, String proveedorArticulo, float totalCompra) {
        this.nombreArticulo = nombreArticulo;
        this.cantidad = cantidad;
        this.estadoOrdenDeCompra = estadoOrdenDeCompra;
        this.fechaLlegada = fechaLlegada;
        this.fechaPedido = fechaPedido;
        this.id = id;
        this.precioIndividual = precioIndividual;
        this.proveedorArticulo = proveedorArticulo;
        this.totalCompra = totalCompra;
    }

    public OrdenDeCompraDTO() {
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstadoOrdenDeCompra() {
        return estadoOrdenDeCompra;
    }

    public void setEstadoOrdenDeCompra(String estadoOrdenDeCompra) {
        this.estadoOrdenDeCompra = estadoOrdenDeCompra;
    }

    public String getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(String fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPrecioIndividual() {
        return precioIndividual;
    }

    public void setPrecioIndividual(float precioIndividual) {
        this.precioIndividual = precioIndividual;
    }

    public String getProveedorArticulo() {
        return proveedorArticulo;
    }

    public void setProveedorArticulo(String proveedorArticulo) {
        this.proveedorArticulo = proveedorArticulo;
    }

    public float getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(float totalCompra) {
        this.totalCompra = totalCompra;
    }
}
