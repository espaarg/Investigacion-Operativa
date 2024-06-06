package com.example.demo.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Table(name = "VentaArticulo")
public class VentaArticulo extends Base{
    @NotNull
    private float subTotal;

    @NotNull
    private int cantidadArticulo;

    @NotNull
    private Date fechaAlta;

    @NotNull
    private Date fechaBaja;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Venta venta;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Articulo articulo;



}
