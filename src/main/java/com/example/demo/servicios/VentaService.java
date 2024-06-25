package com.example.demo.servicios;


import com.example.demo.dtos.CrearVentaDTO;
import com.example.demo.dtos.VentaDTO;
import com.example.demo.entidades.Venta;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


public interface VentaService extends BaseService<Venta, Long> {

    List<Venta> search(String nombre) throws Exception;

    List<VentaDTO> traerTodasVentas() throws Exception;

    List<Venta> findVentasEntreFechas(String fechaDesde, String fechaHasta) throws Exception;

    String crearVenta(CrearVentaDTO ventaDTO) throws Exception;
}