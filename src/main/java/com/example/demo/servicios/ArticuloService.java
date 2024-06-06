package com.example.demo.servicios;

import com.example.demo.entidades.Articulo;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ArticuloService extends BaseService<Articulo, Long> {

    /*    List<ControlStockDTO> controlStockInsuficiente() throws Exception;

        List<ControlStockDTO> controlStockBajo() throws Exception;*/
    List<Articulo> search(String nombre) throws Exception;

/*
    Page<Articulo> search(String denominacion, Number min, Number max, Number stockMenor, Number minStock, Number maxStock, Pageable pageable) throws Exception;
*/
}

