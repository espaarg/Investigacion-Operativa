package com.example.demo.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name="articulos")
public class Articulo extends Base {

    @NotNull
    private String nombre;

    @NotNull
    private float precioCompra;

    @NotNull
    private int stockActual;

    @NotNull
    private int stockDeSeguridad;

    private int loteOptimo;

    private float cgiArticulo;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaModificacion;

}