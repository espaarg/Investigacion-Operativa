package com.example.demo.servicios;

import com.example.demo.entidades.PedidoArticulo;
import com.example.demo.repositorios.BaseRepository;
import com.example.demo.repositorios.PedidoArticuloRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class PedidoArticuloServiceImpl extends BaseServiceImpl<PedidoArticulo,Long> implements PedidoArticuloService{


    @Autowired
    private PedidoArticuloRepository pedidoArticuloRepository;

    public PedidoArticuloServiceImpl(BaseRepository<PedidoArticulo, Long> baseRepository, PedidoArticuloRepository pedidoArticuloRepository){
        super(baseRepository);
        this.pedidoArticuloRepository = pedidoArticuloRepository;
    }
    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public List<PedidoArticulo> search(String filtro) throws Exception {
        return null;
    }

    @Override
    public org.springframework.data.domain.Page<PedidoArticulo> search(String filtro, org.springframework.data.domain.Pageable pageable) throws Exception {
        return null;
    }
}
