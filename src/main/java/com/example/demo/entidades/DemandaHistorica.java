package com.example.demo.entidades;

import com.example.demo.enums.CantidadPeriodo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DemandaHistorica extends Base{

    @Enumerated(EnumType.STRING)
    private CantidadPeriodo periodo;

    @NotNull
    private int cantidadVendida;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    private Articulo articulo;

    */

    /*
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

     */

    @Temporal(TemporalType.DATE)
    private Date fechaBaja;

   /* @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Venta venta;


    @OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DHistoricaVenta> dHistoricaVentaList = new ArrayList<>();

    public void AgregarDHistoricaVenta (DHistoricaVenta dHistoricaVenta){
        dHistoricaVentaList.add(dHistoricaVenta);
    }
    */

}
