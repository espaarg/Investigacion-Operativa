package com.example.demo.servicios;

import com.example.demo.dtos.DemandaHistoricaDTO;
import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.DemandaHistorica;

import java.util.Date;
import java.util.List;

public interface DemandaHistoricaService extends BaseService<DemandaHistorica, Long>{

    void crearDemandaHistorica(Long idArticulo, String fechaDesde, String fechaHasta) throws Exception;

    void crearDemandaH(Long id, String fechaIni, String fechaFin) throws Exception;

    List<DemandaHistoricaDTO> traerTodasDemandasH() throws Exception;
}
