package com.example.demo.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

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
    private final int diasDemora = 8;

    @NotNull
    private double costoPedido;

   /*
    private String proveedorPredeterminado ="prov 1";

    */

    public double getCostoPedido() {
        return costoPedido;
    }
}