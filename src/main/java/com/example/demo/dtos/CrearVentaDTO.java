package com.example.demo.dtos;

import java.util.List;

public class CrearVentaDTO {

    private List<VentaDetalleDTO> articulos;

    // Getters y setters


    public CrearVentaDTO(List<VentaDetalleDTO> articulos) {
        this.articulos = articulos;
    }

    public CrearVentaDTO() {
    }

    public List<VentaDetalleDTO> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<VentaDetalleDTO> articulos) {
        this.articulos = articulos;
    }

    public static class VentaDetalleDTO {
        private Long articuloId;
        private int cantidad;

        // Getters y setters


        public VentaDetalleDTO(Long articuloId, int cantidad) {
            this.articuloId = articuloId;
            this.cantidad = cantidad;
        }

        public VentaDetalleDTO() {
        }

        public Long getArticuloId() {
            return articuloId;
        }

        public void setArticuloId(Long articuloId) {
            this.articuloId = articuloId;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
    }
}
