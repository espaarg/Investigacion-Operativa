package com.example.demo.servicios;


import com.example.demo.dtos.CrearVentaDTO;
import com.example.demo.dtos.VentaDTO;
import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.OrdenDeCompra;
import com.example.demo.entidades.Venta;
import com.example.demo.entidades.VentaArticulo;
import com.example.demo.repositorios.*;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service

public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService{

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private ArticuloRepository articuloRepository;
    @Autowired
    private VentaArticuloRepository ventaArticuloRepository;

    public VentaServiceImpl(BaseRepository<Venta, Long> baseRepository, VentaRepository ventaRepository) {
        super(baseRepository);
        this.ventaRepository = ventaRepository;
    }


    @Override
    public List<Venta> search(String nombre) throws Exception {
        try {
            List<Venta> ventas = ventaRepository.searchNativo(nombre);
            return ventas;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<VentaDTO> traerTodasVentas() throws Exception {
        try {
            List<Venta> ventas = ventaRepository.traerTodasVentas();
            List<VentaDTO> ventasDTO = new java.util.ArrayList<>(List.of());
            for (Venta venta : ventas){
                VentaDTO ventaN = new VentaDTO();
                ventaN.setId(venta.getId());
                ventaN.setFecha(venta.getFechaVenta().toString());
                ventaN.setMontoTotal((long) venta.getMontoTotal());
                ventasDTO.add(ventaN);
            }
            return ventasDTO;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Venta> findVentasEntreFechas(String fechaDesde, String fechaHasta) throws Exception{
        try {
            List<Venta> ventas = ventaRepository.findVentasEntreFechas(fechaDesde, fechaHasta);
            return ventas;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String crearVenta(CrearVentaDTO ventaDTO) throws Exception {
        try {
            LocalDate date = LocalDate.now();
            Date date1 = java.sql.Date.valueOf(date);
            Venta venta = new Venta();
            float sumaTotal = 0;

            venta.setFechaVenta(date1);
            venta = ventaRepository.save(venta);

            List<CrearVentaDTO.VentaDetalleDTO> detalles = ventaDTO.getArticulos();

            for (CrearVentaDTO.VentaDetalleDTO v : detalles) {
                VentaArticulo ventaArticulo = new VentaArticulo();
                Articulo articulo = articuloRepository.getReferenceById(v.getArticuloId());
                float precio = articulo.getPrecioCompra() + articulo.getPrecioCompra() * 0.5f;

                ventaArticulo.setVenta(venta);
                ventaArticulo.setArticulo(articulo);
                ventaArticulo.setCantidadArticulo(v.getCantidad());
                ventaArticulo.setFechaAlta(date1);
                ventaArticulo.setSubTotal(precio * v.getCantidad());

                sumaTotal += precio * v.getCantidad();

                if (articulo.getStockActual() - v.getCantidad() < 0) {
                    articulo.setStockActual(0);
                } else {
                    articulo.setStockActual(articulo.getStockActual() - v.getCantidad());
                }

                ventaArticuloRepository.save(ventaArticulo);
                articuloRepository.save(articulo);

                ventaArticulo.setVenta(venta);
            }

            venta.setMontoTotal(sumaTotal);

            ventaRepository.save(venta);

            return "Venta creada";

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }


}