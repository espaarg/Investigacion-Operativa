package com.example.demo.servicios;


import com.example.demo.dtos.OrdenDeCompraDTO;
import com.example.demo.dtos.PedidoArticuloDTO;
import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.OrdenDeCompra;
import com.example.demo.entidades.PedidoArticulo;
import com.example.demo.entidades.ProveedorArticulo;
import com.example.demo.repositorios.*;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class OrdenDeCompraServiceImpl extends BaseServiceImpl<OrdenDeCompra, Long> implements OrdenDeCompraService{

    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;
    @Autowired
    private PedidoArticuloRepository pedidoArticuloRepository;
    @Autowired
    private PedidoArticuloServiceImpl pedidoArticuloServiceImpl;
    @Autowired
    private ArticuloRepository articuloRepository;
    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

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
    public void crearOrdenDeCompra(List<PedidoArticuloDTO> pedidoArticulos) throws Exception {
        OrdenDeCompra ordenDeCompra = new OrdenDeCompra();
        LocalDate date = LocalDate.now();
        Date date1 = java.sql.Date.valueOf(date);
        int cantidadArticulos = 0;
        float sumaTotal = 0;
        ordenDeCompra.setFechaPedido(date1);
        ordenDeCompra =  ordenDeCompraRepository.save(ordenDeCompra);
        long idOrden = ordenDeCompra.getId();
        for (PedidoArticuloDTO pedido: pedidoArticulos){
            int cant = pedido.getCantidad();
            long idArt = pedido.getIdArticulo();
            Articulo art = articuloRepository.getReferenceById(idArt);
            cantidadArticulos=cantidadArticulos+cant;
            sumaTotal=sumaTotal+art.getPrecioCompra();
            pedidoArticuloServiceImpl.crearPedidoArticulo(cant,idArt,idOrden);
            ordenDeCompra.setProveedorArticulo(proveedorArticuloRepository.getReferenceById(pedido.getIdProveedor()));
        }
        ordenDeCompra.setTotalArticulos(cantidadArticulos);
        ordenDeCompra.setTotalCompra(sumaTotal);
        ordenDeCompraRepository.save(ordenDeCompra);
    }

    @Override
    public List<OrdenDeCompraDTO> traerTodasOrdenes() throws Exception {
        List<OrdenDeCompra> ordenDeCompras = ordenDeCompraRepository.traerTodasOrdenes();
        List<OrdenDeCompraDTO> ordenDeCompraDTOS = new ArrayList<>();
        for (OrdenDeCompra ordenDeCompra: ordenDeCompras){
            OrdenDeCompraDTO o1 = new OrdenDeCompraDTO();
            o1.setId(ordenDeCompra.getId());
            o1.setTotalCompra(ordenDeCompra.getTotalCompra());
            o1.setTotalArticulos(ordenDeCompra.getTotalArticulos());
            ProveedorArticulo prov = ordenDeCompra.getProveedorArticulo();
            o1.setProveedorArticulo(prov.getNombreProveedor());
            o1.setEstadoOrdenDeCompra(o1.getEstadoOrdenDeCompra());
            o1.setFechaPedido(ordenDeCompra.getFechaPedido().toString());
            ordenDeCompraDTOS.add(o1);
        }
        return ordenDeCompraDTOS;
    }

/*
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
*/


    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }


}

