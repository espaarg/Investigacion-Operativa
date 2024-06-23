package com.example.demo.servicios;

import com.example.demo.dtos.PrediccionPMPDTO;
import com.example.demo.entidades.PrediccionDemanda;

public interface PrediccionDemandaService extends BaseService<PrediccionDemanda, Long>{

    public double predecirDemandaPMP(PrediccionPMPDTO prediccionPMPDTO) throws Exception;

}
