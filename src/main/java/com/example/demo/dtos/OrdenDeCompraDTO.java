package com.example.demo.dtos;

public class OrdenDeCompraDTO {
    private long id;
    private float totalCompra;
    private int totalArticulos;
    private String fechaPedido;
    private String estadoOrdenDeCompra;
    private String proveedorArticulo;

    public OrdenDeCompraDTO(String estadoOrdenDeCompra, String fechaPedido, long id, String proveedorArticulo, int totalArticulos, float totalCompra) {
        this.estadoOrdenDeCompra = estadoOrdenDeCompra;
        this.fechaPedido = fechaPedido;
        this.id = id;
        this.proveedorArticulo = proveedorArticulo;
        this.totalArticulos = totalArticulos;
        this.totalCompra = totalCompra;
    }

    public OrdenDeCompraDTO() {
    }

    public String getEstadoOrdenDeCompra() {
        return estadoOrdenDeCompra;
    }

    public void setEstadoOrdenDeCompra(String estadoOrdenDeCompra) {
        this.estadoOrdenDeCompra = estadoOrdenDeCompra;
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

    public String getProveedorArticulo() {
        return proveedorArticulo;
    }

    public void setProveedorArticulo(String proveedorArticulo) {
        this.proveedorArticulo = proveedorArticulo;
    }

    public int getTotalArticulos(int totalArticulos) {
        return this.totalArticulos;
    }

    public void setTotalArticulos(int totalArticulos) {
        this.totalArticulos = totalArticulos;
    }

    public float getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(float totalCompra) {
        this.totalCompra = totalCompra;
    }
}
