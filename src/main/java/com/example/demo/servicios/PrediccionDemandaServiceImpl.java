package com.example.demo.servicios;

import com.example.demo.entidades.PrediccionDemanda;
import com.example.demo.repositorios.BaseRepository;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service

public class PrediccionDemandaServiceImpl extends BaseServiceImpl<PrediccionDemanda, Long> implements PrediccionDemandaService{
    public PrediccionDemandaServiceImpl(BaseRepository<PrediccionDemanda, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }
}
