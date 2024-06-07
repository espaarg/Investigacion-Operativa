package com.example.demo.servicios;


import com.example.demo.entidades.Venta;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface VentaService extends BaseService<Venta, Long> {

    List<Venta> search(String nombre) throws Exception;


}