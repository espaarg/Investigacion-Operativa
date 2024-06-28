package com.example.demo.entidades;

import com.example.demo.enums.*;
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
public class PrediccionDemanda extends Base{

    private int porcentajeDeError;

    private double error;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    private CantidadPeriodo cantidadPeriodo;

    @Enumerated(EnumType.STRING)
    private MetodoPrediccion metodoPrediccion;

    @ManyToOne
    private Articulo articulo;

    private double valorPrediccion;

}