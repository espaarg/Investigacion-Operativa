package com.example.demo.servicios;

import com.example.demo.dtos.OrdenDeCompraDTO;
import com.example.demo.dtos.PedidoArticuloDTO;
import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.OrdenDeCompra;

import java.util.List;


public interface OrdenDeCompraService extends BaseService<OrdenDeCompra, Long> {

    List<OrdenDeCompra> search(String nombre) throws Exception;

    List<OrdenDeCompra> filtrarOrdenDeCompraPorEstado(String estado) throws Exception;


    boolean buscarOrdenesActivas(Articulo articulo) throws Exception;

    void crearOrdenDeCompra(Long articuloID, int cantidad, Long proveedor) throws Exception;


    List<OrdenDeCompraDTO> traerTodasOrdenes() throws Exception;

    void confirmarOrden(Long id) throws Exception;
}
