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

@Table(name = "PrediccionDemanda")
public class PrediccionDemanda extends Base{

    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CantidadPeriodo periodo;

    @NotNull
    private int porcentajeDeError;

    @NotNull
    private Date fechaPedido;

    @NotNull
    private Date fechaInicio;

    @NotNull
    private Date fechaFin;

    @Enumerated(EnumType.STRING)
    private CantidadPeriodo cantidadPeriodo;

    @Enumerated(EnumType.STRING)
    private MetodoCalculoError metodoCalculoError;

    @Enumerated(EnumType.STRING)
    private MetodoPrediccion metodoPrediccion;

    @Enumerated(EnumType.STRING)
    private FijacionErrorAceptable fijacionErrorAceptable;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @NotNull
    @Builder.Default
    private List<DHistoricaDPrediccion> dHistoricaDPrediccionList = new ArrayList<>();

    public void AgregarDHistoricaDprediccion(DHistoricaDPrediccion dam){

        dHistoricaDPrediccionList.add(dam);
    }





}
