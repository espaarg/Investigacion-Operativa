package com.example.demo.dtos;

import com.example.demo.entidades.Articulo;
import lombok.Data;

@Data
public class PrediccionPMSEDTO {
    private int mesAPredecir;
    private int anioAPredecir;
    private Articulo articulo;
    private double alfa;
}
