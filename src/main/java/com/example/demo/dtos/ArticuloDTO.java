package com.example.demo.dtos;

public class ArticuloDTO {

    int cantAPedir;
    int cantMax;
    Float cgiArticulo;
    float costoAlmacenamiento;
    String fechaAlta;
    float id;
    int loteOptimo;
    String modeloInventario;
    String nombre;
    float precioCompra;
    String proveedorArticulo;
    int puntoPedido;
    int stockActual;
    int stockDeSeguridad;
    int tiempoEntrePedidos;


    public ArticuloDTO(int cantAPedir, int cantMax, float cgiArticulo, float costoAlmacenamiento, String fechaAlta, float id, int loteOptimo, String modeloInventario, String nombre, float precioCompra, String proveedorArticulo, int puntoPedido, int stockActual, int stockDeSeguridad, int tiempoEntrePedidos) {
        this.cantAPedir = cantAPedir;
        this.cantMax = cantMax;
        this.cgiArticulo = cgiArticulo;
        this.costoAlmacenamiento = costoAlmacenamiento;
        this.fechaAlta = fechaAlta;
        this.id = id;
        this.loteOptimo = loteOptimo;
        this.modeloInventario = modeloInventario;
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.proveedorArticulo = proveedorArticulo;
        this.puntoPedido = puntoPedido;
        this.stockActual = stockActual;
        this.stockDeSeguridad = stockDeSeguridad;
        this.tiempoEntrePedidos = tiempoEntrePedidos;
    }

    public ArticuloDTO() {
    }

    public int getCantAPedir() {
        return cantAPedir;
    }

    public void setCantAPedir(int cantAPedir) {
        this.cantAPedir = cantAPedir;
    }

    public int getCantMax() {
        return cantMax;
    }

    public void setCantMax(int cantMax) {
        this.cantMax = cantMax;
    }

    public float getCgiArticulo() {
        return cgiArticulo;
    }

    public void setCgiArticulo(Float cgiArticulo) {
        this.cgiArticulo = cgiArticulo;
    }

    public float getCostoAlmacenamiento() {
        return costoAlmacenamiento;
    }

    public void setCostoAlmacenamiento(float costoAlmacenamiento) {
        this.costoAlmacenamiento = costoAlmacenamiento;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public float getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLoteOptimo() {
        return loteOptimo;
    }

    public void setLoteOptimo(int loteOptimo) {
        this.loteOptimo = loteOptimo;
    }

    public String getModeloInventario() {
        return modeloInventario;
    }

    public void setModeloInventario(String modeloInventario) {
        this.modeloInventario = modeloInventario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getProveedorArticulo() {
        return proveedorArticulo;
    }

    public void setProveedorArticulo(String proveedorArticulo) {
        this.proveedorArticulo = proveedorArticulo;
    }

    public int getPuntoPedido() {
        return puntoPedido;
    }

    public void setPuntoPedido(int puntoPedido) {
        this.puntoPedido = puntoPedido;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getStockDeSeguridad() {
        return stockDeSeguridad;
    }

    public void setStockDeSeguridad(int stockDeSeguridad) {
        this.stockDeSeguridad = stockDeSeguridad;
    }

    public int getTiempoEntrePedidos() {
        return tiempoEntrePedidos;
    }

    public void setTiempoEntrePedidos(int tiempoEntrePedidos) {
        this.tiempoEntrePedidos = tiempoEntrePedidos;
    }
}
