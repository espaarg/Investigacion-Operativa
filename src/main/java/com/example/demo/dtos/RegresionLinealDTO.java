package com.example.demo.dtos;

import com.example.demo.entidades.Articulo;
import com.example.demo.enums.MetodoPrediccion;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Data
public class RegresionLinealDTO {
    private int cantidadPeriodos;

    private Articulo articulo;

    private int mesAPredecir;

    private int anioAPredecir;
    private String fechaDesde;
    private String fechaHasta;

}
