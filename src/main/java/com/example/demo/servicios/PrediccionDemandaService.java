package com.example.demo.servicios;

import com.example.demo.dtos.PrediccionDemandaDTO;
import com.example.demo.dtos.RegresionLinealDTO;
import com.example.demo.entidades.PrediccionDemanda;

import java.time.LocalDate;

public interface PrediccionDemandaService extends BaseService<PrediccionDemanda, Long>{

    public double predecirDemandaPMP(PrediccionDemandaDTO prediccionDemandaDTO) throws Exception;

    public double calcularPromedioMovilMesAnterior(Long idArticulo, LocalDate fechaDesdeLocalDate) throws Exception;

    public double predecirDemandaPMSuavizadoExponencial(PrediccionDemandaDTO prediccionPMSEDTO) throws Exception;

    public double predecirDemandaEstacional(PrediccionDemandaDTO prediccionEstacionalDTO) throws Exception;

    public void predecirDemandas(PrediccionDemandaDTO prediccionDemandaDTO) throws Exception;
    public double predecirDemandaEstacional(PrediccionEstacionalDTO prediccionEstacionalDTO) throws Exception;
    public Integer calcularRegresionLineal(RegresionLinealDTO regresionLinealDTO) throws Exception;



}
