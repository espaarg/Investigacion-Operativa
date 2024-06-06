package com.example.demo.entidades;

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
public class DHistoricaDPrediccion extends Base{

    @NotNull
    private String nombre;

    @NotNull
    private Date fechaAlta;

    @NotNull
    private Date fechaBaja;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private DemandaHistorica dHistorica;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private PrediccionDemanda dPrediccion;



}