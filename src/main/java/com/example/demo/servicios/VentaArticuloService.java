package com.example.demo.servicios;


import com.example.demo.dtos.VentaArticuloDTO;
import com.example.demo.entidades.VentaArticulo;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface VentaArticuloService extends BaseService<VentaArticulo, Long> {

    List<VentaArticulo> search(String nombre) throws Exception;

    List<VentaArticuloDTO> todoDetalleVenta(Long id) throws Exception;



}