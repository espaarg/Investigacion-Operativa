package com.example.demo.servicios;

import com.example.demo.entidades.PedidoArticulo;
import com.example.demo.entidades.ProveedorArticulo;
import com.example.demo.repositorios.BaseRepository;
import com.example.demo.repositorios.PedidoArticuloRepository;
import com.example.demo.repositorios.ProveedorArticuloRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class ProveedorArticuloServiceImpl extends BaseServiceImpl<ProveedorArticulo, Long> implements ProveedorArticuloService{


    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

    public ProveedorArticuloServiceImpl(BaseRepository<ProveedorArticulo, Long> baseRepository, ProveedorArticuloRepository proveedorArticuloRepository) {
        super(baseRepository);
        this.proveedorArticuloRepository = proveedorArticuloRepository;
    }
    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public List<ProveedorArticulo> search(String codigo) throws Exception {
        return null;
    }

    @Override
    public org.springframework.data.domain.Page<ProveedorArticulo> search(String codigo, org.springframework.data.domain.Pageable pageable) throws Exception {
        return null;
    }
}