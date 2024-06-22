package com.example.demo.dtos;

import com.example.demo.entidades.Articulo;
import lombok.Data;

import java.util.List;

@Data
public class PrediccionPMPDTO {

    private List<Double> coeficientesPonderacion;
    private Articulo articulo;

    private int cantidadPeriodosAtras;
    private int mesAPredecir;
    private int anioAPredecir;

}
