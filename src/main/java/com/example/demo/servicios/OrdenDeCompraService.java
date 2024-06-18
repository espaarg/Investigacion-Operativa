package com.example.demo.servicios;

import com.example.demo.dtos.OrdenDeCompraDTO;
import com.example.demo.dtos.PedidoArticuloDTO;
import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.OrdenDeCompra;

import com.example.demo.entidades.PedidoArticulo;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrdenDeCompraService extends BaseService<OrdenDeCompra, Long> {

    List<OrdenDeCompra> search(String nombre) throws Exception;

    List<OrdenDeCompra> filtrarOrdenDeCompraPorEstado(String estado) throws Exception;


    boolean buscarOrdenesActivas(Articulo articulo) throws Exception;

    void crearOrdenDeCompra(List<PedidoArticuloDTO> pedidoArticulos) throws Exception;


    List<OrdenDeCompraDTO> traerTodasOrdenes() throws Exception;
}
