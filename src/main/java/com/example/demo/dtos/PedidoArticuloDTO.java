package com.example.demo.dtos;

public class PedidoArticuloDTO {
    private Integer cantidad;
    private long idArticulo;
    private long idProveedor;

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public PedidoArticuloDTO(int cantidad, long idArticulo) {
        this.cantidad = cantidad;
        this.idArticulo = idArticulo;
    }

    /*
    public PedidoArticuloDTO(int cantidad, long idArticulo, long idProveedor) {
        this.cantidad = cantidad;
        this.idArticulo = idArticulo;
        this.idProveedor = idProveedor;
    }
     */

    public long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public PedidoArticuloDTO() {
    }
}
