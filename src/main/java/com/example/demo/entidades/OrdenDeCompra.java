package com.example.demo.entidades;


import com.example.demo.enums.EstadoOrdenDeCompra;
import com.example.demo.enums.FijacionErrorAceptable;
import com.example.demo.enums.Proveedor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrdenDeCompra extends Base{

    private String descripcion;

    @NotNull
    private int TotalCompra;

    @NotNull
    private int TotalArticulos;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaPedido;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;

    @Temporal(TemporalType.DATE) //hace falta?
    private Date fechaLlegada;

    @Enumerated(EnumType.STRING)
    private EstadoOrdenDeCompra estadoOrdenDeCompra;

    @Enumerated(EnumType.STRING)
    private Proveedor proveedor;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @NotNull
    @Builder.Default
    private List<PedidoArticulo> pedidoArticulos = new ArrayList<>();

    /*
     public void AgregarPedidoArticulo (PedidoArticulo pedidoArticulo){
        pedidoArticulos.add(pedidoArticulo);
    }
     */

}