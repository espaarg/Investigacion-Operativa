package com.example.demo.dtos;

import com.example.demo.entidades.Articulo;
import com.example.demo.enums.CantidadPeriodo;
import com.example.demo.enums.MetodoPrediccion;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class PrediccionDemandaDTO {

    private String cantidadDePredicciones;
    private Long idArticulo;
    private int mesAPredecir;
    private int anioAPredecir;

    //datos para crear prediccion que NO SE PIDEN EN EL FRONT
    private double error;
    private double porcentajeDeError;
    private double prediccion;

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
