package com.example.demo.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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

public class PedidoArticulo extends Base{

    @NotNull
    private int cantidad;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Articulo articulo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private OrdenDeCompra ordenDeCompra;

    @NotNull
    private Date fechaAlta;

    @NotNull
    private Date fechaBaja;


}
