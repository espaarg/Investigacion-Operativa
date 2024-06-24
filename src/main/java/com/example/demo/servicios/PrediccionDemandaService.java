package com.example.demo.servicios;

import com.example.demo.dtos.PrediccionEstacionalDTO;
import com.example.demo.dtos.PrediccionPMPDTO;
import com.example.demo.dtos.PrediccionPMSEDTO;
import com.example.demo.entidades.PrediccionDemanda;

import java.time.LocalDate;

public interface PrediccionDemandaService extends BaseService<PrediccionDemanda, Long>{

    public double predecirDemandaPMP(PrediccionPMPDTO prediccionPMPDTO) throws Exception;

    public double calcularPromedioMovilMesAnterior(Long idArticulo, LocalDate fechaDesdeLocalDate) throws Exception;

    public double predecirDemandaPMSuavizadoExponencial(PrediccionPMSEDTO prediccionPMSEDTO) throws Exception;

    public double predecirDemandaEstacional(PrediccionEstacionalDTO prediccionEstacionalDTO) throws Exception;


}
