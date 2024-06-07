package com.example.demo.servicios;


import com.example.demo.entidades.OrdenDeCompra;
import com.example.demo.entidades.VentaArticulo;
import com.example.demo.repositorios.BaseRepository;
import com.example.demo.repositorios.OrdenDeCompraRepository;
import com.example.demo.repositorios.VentaArticuloRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service

public class VentaArticuloServiceImpl extends BaseServiceImpl<VentaArticulo, Long> implements VentaArticuloService{

    @Autowired
    private VentaArticuloRepository ventaArticuloRepository;

    public VentaArticuloServiceImpl(BaseRepository<VentaArticulo, Long> baseRepository, VentaArticuloRepository ventaArticuloRepository) {
        super(baseRepository);
        this.ventaArticuloRepository = ventaArticuloRepository;
    }


    @Override
    public List<VentaArticulo> search(String nombre) throws Exception {
        try {
            List<VentaArticulo> ventaArticulos = ventaArticuloRepository.searchNativo(nombre);
            return ventaArticulos;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }


}