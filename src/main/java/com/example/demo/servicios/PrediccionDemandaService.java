package com.example.demo.servicios;

import com.example.demo.dtos.GETPrediccionDemandaDTO;
import com.example.demo.dtos.PrediccionDemandaDTO;
import com.example.demo.dtos.RegresionLinealDTO;
import com.example.demo.entidades.PrediccionDemanda;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PrediccionDemandaService extends BaseService<PrediccionDemanda, Long>{

    public double predecirDemandaPMP(PrediccionDemandaDTO prediccionDemandaDTO) throws Exception;

    public double calcularPromedioMovilMesAnterior(Long idArticulo, LocalDate fechaDesdeLocalDate) throws Exception;

    public double predecirDemandaPMSuavizadoExponencial(PrediccionDemandaDTO prediccionPMSEDTO) throws Exception;

    public double predecirDemandaEstacional(PrediccionDemandaDTO prediccionEstacionalDTO) throws Exception;

    public void predecirDemandas(PrediccionDemandaDTO prediccionDemandaDTO, int contador) throws Exception;

    @Transactional
    public void crearPDemanda(PrediccionDemandaDTO prediccionDemandaDTO, Date fechaDesde, Date fechaHasta) throws Exception;

    public Integer calcularRegresionLineal(RegresionLinealDTO regresionLinealDTO) throws Exception;

    public void servicioParaPredecir(PrediccionDemandaDTO prediccionDemandaDTO) throws Exception;


    List<GETPrediccionDemandaDTO> traerTodasPredicciones() throws Exception;
}
