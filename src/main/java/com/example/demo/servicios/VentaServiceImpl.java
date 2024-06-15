package com.example.demo.servicios;


import com.example.demo.dtos.VentaDTO;
import com.example.demo.entidades.OrdenDeCompra;
import com.example.demo.entidades.Venta;
import com.example.demo.repositorios.BaseRepository;
import com.example.demo.repositorios.OrdenDeCompraRepository;
import com.example.demo.repositorios.VentaRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

@Service

public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService{

    @Autowired
    private VentaRepository ventaRepository;

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
    public List<Venta> traerTodasVentas() throws Exception {
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
            return ventas;
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
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }


}