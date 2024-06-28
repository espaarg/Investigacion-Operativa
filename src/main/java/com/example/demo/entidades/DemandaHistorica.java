package com.example.demo.entidades;

import com.example.demo.enums.CantidadPeriodo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Articulo articulo;

    @Temporal(TemporalType.DATE)
    private Date fechaBaja;

    public double getCantidadVendida() {

        return cantidadVendida;
    }


}
