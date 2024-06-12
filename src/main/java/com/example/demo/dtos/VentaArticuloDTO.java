package com.example.demo.dtos;

import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.TupleTransformer;

@Data
public class VentaArticuloDTO {

    private Long id;
    private String nombreArticulo;
    private int cantidadArticulo;
    private float subTotal;

    public VentaArticuloDTO( Long id, String nombreArticulo,int cantidadArticulo, float subTotal) {
        this.cantidadArticulo = cantidadArticulo;
        this.id = id;
        this.nombreArticulo = nombreArticulo;
        this.subTotal = subTotal;
    }

    public VentaArticuloDTO(Tuple tuple) {
        this.cantidadArticulo = (int) tuple.get("cantidad_articulo");
        this.id = (Long) tuple.get("id");
        this.nombreArticulo = (String) tuple.get("articulo_nombre");
        this.subTotal = (Float) tuple.get("sub_total");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidadArticulo() {
        return cantidadArticulo;
    }

    public void setCantidadArticulo(int cantidadArticulo) {
        this.cantidadArticulo = cantidadArticulo;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }
}

