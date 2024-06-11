package com.example.demo.servicios;

import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.PedidoArticulo;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ArticuloService extends BaseService<Articulo, Long> {

    List<Articulo> traerTodosArticulos() throws Exception;

    /*    List<ControlStockDTO> controlStockInsuficiente() throws Exception;

            List<ControlStockDTO> controlStockBajo() throws Exception;*/
    List<Articulo> traerUnArticuloNombre(String nombre) throws Exception;
    public Articulo traerUnArticuloId(Long id) throws Exception;
    List<Articulo> traerArticuloBajoStock(int stockDeSeguridad, int stockActual) throws Exception;


/*
    Page<Articulo> search(String denominacion, Number min, Number max, Number stockMenor, Number minStock, Number maxStock, Pageable pageable) throws Exception;
*/
}

