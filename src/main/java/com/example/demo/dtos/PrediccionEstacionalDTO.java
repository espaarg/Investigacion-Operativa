package com.example.demo.dtos;

import com.example.demo.entidades.Articulo;
import lombok.Data;

@Data
public class PrediccionEstacionalDTO {
    private Articulo articulo;
    private int cantidadDePeriodos;
    private int cantidadDeaniosAtras;
    private int cantUnidadesEsperadas;
    private int mesAPredecir;
    private int anioAPredecir;

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getCantidadDePeriodos() {
        return cantidadDePeriodos;
    }

    public void setCantidadDePeriodos(int cantidadDePeriodos) {
        this.cantidadDePeriodos = cantidadDePeriodos;
    }

    public int getCantidadDeaniosAtras() {
        return cantidadDeaniosAtras;
    }

    public void setCantidadDeaniosAtras(int cantidadDeaniosAtras) {
        this.cantidadDeaniosAtras = cantidadDeaniosAtras;
    }

    public int getCantUnidadesEsperadas() {
        return cantUnidadesEsperadas;
    }

    public void setCantUnidadesEsperadas(int cantUnidadesEsperadas) {
        this.cantUnidadesEsperadas = cantUnidadesEsperadas;
    }

    public int getMesAPredecir() {
        return mesAPredecir;
    }

    public void setMesAPredecir(int mesAPredecir) {
        this.mesAPredecir = mesAPredecir;
    }

    public int getAnioAPredecir() {
        return anioAPredecir;
    }

    public void setAnioAPredecir(int anioAPredecir) {
        this.anioAPredecir = anioAPredecir;
    }
}
