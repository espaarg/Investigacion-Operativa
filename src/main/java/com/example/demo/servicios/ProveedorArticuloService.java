package com.example.demo.servicios;

import com.example.demo.entidades.PedidoArticulo;
import com.example.demo.entidades.ProveedorArticulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProveedorArticuloService extends BaseService<ProveedorArticulo, Long>{

    List<ProveedorArticulo> traerTodosProveedores() throws Exception;
    List<ProveedorArticulo> search(String codigo) throws Exception;
    Page<ProveedorArticulo> search(String codigo, Pageable pageable) throws Exception;
    ProveedorArticulo traerProveedorPorNombre(String nombre);
}
