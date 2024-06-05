package com.example.demo.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
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

public class Articulo extends Base {

    @NotNull
    private String nombre;

    @NotNull
    private float precioCompra;

    @NotNull
    private int stockActual;

    @NotNull
    private int stockMinimo;

    @NotNull
    private Date fechaAlta;

    @NotNull
    private Date fechaModificacion;

}