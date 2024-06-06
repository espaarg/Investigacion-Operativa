package com.example.demo.entidades;

import com.example.demo.enums.EstadoVenta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Table(name = "Venta")
public class Venta extends Base{

        @NotNull
        private String detalle;

        @NotNull
        private int montoTotal;

        @NotNull
        private Date fechaVenta;

        @NotNull
        private Date fechaAlta;

        @NotNull
        @OneToMany(mappedBy = "venta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        private List<VentaArticulo> ventaArticulos;

        @NotNull
        @Enumerated(EnumType.STRING)
        private EstadoVenta estadoVenta;



}
