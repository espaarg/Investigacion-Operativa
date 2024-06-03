package com.example.demo.entidades;

import com.example.demo.enums.CantidadPeriodo;
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
public class DemandaHistorica extends Base{

    private String nombre;

    @NotNull
    private CantidadPeriodo periodo;

    @NotNull
    private int cantidadVendida;

    @NotNull
    private int montoTotal;

    @NotNull
    private Date fechaInicio;

    @NotNull
    private Date fechaFin;

    @NotNull
    private Date fechaAlta;

    private Date fechaBaja;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Venta venta;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Articulo articulo;

}
