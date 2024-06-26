package com.example.demo.servicios;

import com.example.demo.dtos.DemandaHistoricaDTO;
import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.DemandaHistorica;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface DemandaHistoricaService extends BaseService<DemandaHistorica, Long> {

    void crearDemandaHistorica(Long idArticulo, String fechaDesde, String fechaHasta) throws Exception;

    void crearDemandaH(Long id, String fechaIni, String fechaFin) throws Exception;

    List<DemandaHistoricaDTO> traerTodasDemandasH() throws Exception;

    void eliminarDemandaHistorica(Long id) throws Exception;

    Integer obtenerDemandaAnual(Long idArticulo) throws Exception;

    Integer buscarDemandaAnual(Long idArticulo, Date fechaDesde, Date fechaHasta) throws Exception;
    Integer calcularDemandaHistoricaArticulo(Long idArticulo, String fechaDesde, String fechaHasta);
    Integer calcularDemandaHistorica(Long idArticulo, String fechaDesde, String fechaHasta);

}