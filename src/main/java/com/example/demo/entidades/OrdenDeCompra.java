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


    private float totalCompra;

    private int totalArticulos;

    @Temporal(TemporalType.DATE)
    private Date fechaPedido;

    @Temporal(TemporalType.DATE)
    private Date fechaBaja;

    @Temporal(TemporalType.DATE) //hace falta?
    private Date fechaLlegada;

    @Enumerated(EnumType.STRING)
    private EstadoOrdenDeCompra estadoOrdenDeCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProveedorArticulo proveedorArticulo;


}