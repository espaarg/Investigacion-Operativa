package com.example.demo.entidades;

import com.example.demo.enums.ModeloInventario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Articulo extends Base {

    @NotNull
    private String nombre;

    @NotNull
    private float precioCompra;

    @NotNull
    private int stockActual;

    private float costoAlmacenamiento;

    private int loteOptimo;

    private double cgiArticulo;

    private int puntoPedido;

    @NotNull
    private int stockDeSeguridad;

    @NotNull
    private int tiempoEntrePedidos;

    @NotNull
    private int cantMax;

    @NotNull
    private int cantAPedir;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @Temporal(TemporalType.DATE)
    private Date fechaBaja;

    @Enumerated(EnumType.STRING)
    private ModeloInventario modeloInventario;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProveedorArticulo proveedorArticulo;

}