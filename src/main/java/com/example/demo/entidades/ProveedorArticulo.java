package com.example.demo.entidades;

import com.example.demo.enums.Proveedor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
public class ProveedorArticulo extends Base{

    @NotNull
    private String nombreProveedor;

    @NotNull
    private int diasDemora;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Articulo articulo;


}