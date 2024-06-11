package com.example.demo.servicios;


import com.example.demo.entidades.Venta;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


public interface VentaService extends BaseService<Venta, Long> {

    List<Venta> search(String nombre) throws Exception;

    List<Venta> traerTodasVentas() throws Exception;

    List<Venta> findVentasEntreFechas(Date fechaDesde, Date fechaHasta) throws Exception;
}