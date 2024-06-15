package com.example.demo.servicios;


import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.OrdenDeCompra;
import com.example.demo.entidades.PedidoArticulo;
import com.example.demo.repositorios.BaseRepository;
import com.example.demo.repositorios.OrdenDeCompraRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service

public class OrdenDeCompraServiceImpl extends BaseServiceImpl<OrdenDeCompra, Long> implements OrdenDeCompraService{

    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;

    public OrdenDeCompraServiceImpl(BaseRepository<OrdenDeCompra, Long> baseRepository, OrdenDeCompraRepository ordenDeCompraRepository) {
        super(baseRepository);
        this.ordenDeCompraRepository = ordenDeCompraRepository;
    }


    @Override
    public List<OrdenDeCompra> search(String nombre) throws Exception {
        try {
            List<OrdenDeCompra> ordenDeCompras = ordenDeCompraRepository.searchNativo(nombre);
            return ordenDeCompras;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<OrdenDeCompra> filtrarOrdenDeCompraPorEstado(String estado) throws Exception {
        try {
            List<OrdenDeCompra> ordenDeCompras = ordenDeCompraRepository.filtrarOrdenDeCompraPorEstado(estado);
            return ordenDeCompras;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean buscarOrdenesActivas(Articulo articulo) throws Exception {
        try {
            List<OrdenDeCompra> ordenesPendientes = ordenDeCompraRepository.filtrarOrdenDeCompraPorEstado("Pendiente");
            List<OrdenDeCompra> ordenesPedidas = ordenDeCompraRepository.filtrarOrdenDeCompraPorEstado("Pedida");
            List<OrdenDeCompra> ordenesActivas = new ArrayList<>();;
            ordenesActivas.addAll(ordenesPendientes);
            ordenesActivas.addAll(ordenesPedidas);
            Long art_id= articulo.getId();

            for (OrdenDeCompra orden : ordenesActivas){
                for (PedidoArticulo pedidoArticulo : orden.getPedidoArticulos()){
                    if(pedidoArticulo.getArticulo().getId().equals(art_id)) {
                        return true;
                    }
                }
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return false;
    }


    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }


}

