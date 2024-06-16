package com.example.demo.dtos;

public class PedidoArticuloDTO {
    private int cantidad;
    private long idArticulo;
    private long idProveedor;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public PedidoArticuloDTO(int cantidad, long idArticulo, long idProveedor) {
        this.cantidad = cantidad;
        this.idArticulo = idArticulo;
        this.idProveedor = idProveedor;
    }

    public long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public PedidoArticuloDTO() {
    }
}
