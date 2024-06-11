package com.example.demo.servicios;

import com.example.demo.entidades.DemandaHistorica;

import java.util.Date;

public interface DemandaHistoricaService extends BaseService<DemandaHistorica, Long>{

    void crearDemandaHistorica(Long idArticulo, Date fechaDesde, Date fechaHasta) throws Exception;

}
