package com.example.demo.servicios;


import com.example.demo.dtos.DemandaHistoricaDTO;
import com.example.demo.dtos.OrdenDeCompraDTO;
import com.example.demo.dtos.PedidoArticuloDTO;
import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.OrdenDeCompra;
import com.example.demo.entidades.ProveedorArticulo;
import com.example.demo.enums.EstadoOrdenDeCompra;
import com.example.demo.enums.ModeloInventario;
import com.example.demo.repositorios.*;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class OrdenDeCompraServiceImpl extends BaseServiceImpl<OrdenDeCompra, Long> implements OrdenDeCompraService{

    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;
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
    public void crearOrdenDeCompra(Long articuloID, int cantidad, Long proveedor) throws Exception {
        try {
            OrdenDeCompra ordenDeCompra = new OrdenDeCompra();
            LocalDate date = LocalDate.now();
            Date date1 = java.sql.Date.valueOf(date);
            ordenDeCompra.setFechaPedido(date1);
            Articulo art = articuloRepository.getReferenceById(articuloID);

            // Verificar si hay órdenes activas para el artículo
            if (!buscarOrdenesActivas(art)) {
                throw new Exception("Ya existe una orden activa para el articulo con ID: " + art.getId());
            }

            // Asignamos los valores a la orden de compra
            ordenDeCompra.setEstadoOrdenDeCompra(EstadoOrdenDeCompra.PEDIDA);
            ordenDeCompra.setProveedorArticulo(proveedorArticuloRepository.getReferenceById(proveedor));
            ordenDeCompra.setArticulo(art);
            ordenDeCompra.setPrecioIndividual(art.getPrecioCompra());
            ordenDeCompra.setCantidad(cantidad);
            ordenDeCompra.setTotalCompra(cantidad * art.getPrecioCompra());

            // Guardar la orden de compra
            ordenDeCompraRepository.save(ordenDeCompra);

        } catch (Exception e) {
            // Manejar la excepción y lanzar nuevamente
            throw new Exception("Error al crear la orden de compra: " + e.getMessage(), e);
        }
    }



    @Override
    public List<OrdenDeCompraDTO> traerTodasOrdenes() throws Exception {
        List<Map<String, Object>> ordenDeCompras = ordenDeCompraRepository.traerTodasOrdenes();
        List<OrdenDeCompraDTO> ordenDeCompraDTOS = new ArrayList<>();

        ordenDeCompraDTOS = ordenDeCompras.stream()
                .map(result->new OrdenDeCompraDTO(
                        (String) result.get("nombreArticulo"),
                        ((int) result.get("cantidad")),
                        ((String) result.get("estado")),
                        (result.get("fechaLlegada") != null && !result.get("fechaLlegada").toString().isEmpty())
                                ? result.get("fechaLlegada").toString()
                                : "",                        (String) result.get("fechaPedido").toString(),
                        ((long) result.get("id")),
                        ((float) result.get("precioIndividual")),
                        ((String) result.get("proveedorArticulo")),
                        ((float) result.get("totalCompra"))))
                .collect(Collectors.toList());

        return ordenDeCompraDTOS;
    }

    @Override
    public void confirmarOrden(Long id) {
        OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.getReferenceById(id);
        Articulo articulo = ordenDeCompra.getArticulo();

        LocalDate date = LocalDate.now();
        Date date1 = java.sql.Date.valueOf(date);

        ordenDeCompra.setEstadoOrdenDeCompra(EstadoOrdenDeCompra.RECIBIDA);
        ordenDeCompra.setFechaLlegada(date1);

        articulo.setStockActual(articulo.getStockActual()+ ordenDeCompra.getCantidad());

        ordenDeCompraRepository.save(ordenDeCompra);
        articuloRepository.save(articulo);

    }


    @Override
    public boolean buscarOrdenesActivas(Articulo articulo) throws Exception {
        try {
            List<OrdenDeCompra> ordenesPedidas = ordenDeCompraRepository.filtrarOrdenDeCompraPorEstado("Pedida");
            List<OrdenDeCompra> ordenesActivas = new ArrayList<>();;
            Long art_id= articulo.getId();

            for (OrdenDeCompra orden : ordenesPedidas) {
                Articulo artOrden = orden.getArticulo();
                Long artID = artOrden.getId();
                if (Objects.equals(artID, art_id)) {
                    ordenesActivas.add(orden);
                }
            }
        return ordenesActivas.isEmpty();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }


}

