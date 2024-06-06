package com.example.demo.entidades;

import com.example.demo.enums.EstadoVenta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="venta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Venta extends Base{

        @NotNull
        private String detalle;

        @NotNull
        private int montoTotal;

        @NotNull
        @Temporal(TemporalType.DATE)
        private Date fechaVenta;

        @NotNull
        @Enumerated(EnumType.STRING)
        private EstadoVenta estadoVenta;

        @NotNull
        @OneToMany(mappedBy = "venta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @Builder.Default
        @JoinColumn(name="venta_id")
        private List<VentaArticulo> ventaArticulos;

        public void AgregarVentaArticulo (VentaArticulo ventaArticulo){
                ventaArticulos.add(ventaArticulo);
        }

        @NotNull
        @OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
        @Builder.Default
        @JoinColumn(name="venta_id")
        private List<DHistoricaVenta> dHistoricaVentaList = new ArrayList<>();


        public void AgregarDHistoricaVenta (DHistoricaVenta dHistoricaVenta){
                dHistoricaVentaList.add(dHistoricaVenta);
        }



}
