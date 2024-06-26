package com.example.demo.dtos;

import com.example.demo.entidades.Articulo;
import com.example.demo.enums.MetodoPrediccion;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class PrediccionDemandaDTO {

    private int cantidadDePredicciones;
    private Long idArticulo;
    private int mesAPredecir;
    private int anioAPredecir;

    //datos para crear prediccion
    private double error;

    @Enumerated
    private MetodoPrediccion metodoPrediccion;

    //PMP
    private List<Double> coeficientesPonderacion;
    private int cantidadPeriodosAtrasPMP;

    //PMSE
    private double alfa;

    //Estacional
    private int cantidadDePeriodosEST;
    private int cantidadDeaniosAtrasEST;
    private int cantUnidadesEsperadasEST;

    //Regresion Lineal




}
