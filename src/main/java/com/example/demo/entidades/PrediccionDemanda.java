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

    @NotNull
    private int porcentajeDeError;

    private double error;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaPedido;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Enumerated(EnumType.STRING)
    private CantidadPeriodo cantidadPeriodo;

    @Enumerated(EnumType.STRING)
    private MetodoCalculoError metodoCalculoError;

    @Enumerated(EnumType.STRING)
    private MetodoPrediccion metodoPrediccion;

    @Enumerated(EnumType.STRING)
    private FijacionErrorAceptable fijacionErrorAceptable;

    @ManyToOne
    private Articulo articulo;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    @JoinColumn(name="prediccionDemanda_id")
    private List<DHistoricaDPrediccion> dHistoricaDPrediccionList = new ArrayList<>();

    public void AgregarDHistoricaDprediccion(DHistoricaDPrediccion dHistoricaDPrediccion){

        dHistoricaDPrediccionList.add(dHistoricaDPrediccion);
    }





}