package com.example.demo.repositorios;


import com.example.demo.dtos.VentaArticuloDTO;
import com.example.demo.entidades.VentaArticulo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface VentaArticuloRepository extends BaseRepository<VentaArticulo, Long>{

    @PersistenceContext
    EntityManager entityManager = null;


    @Query(value = "SELECT * FROM articulo a WHERE a.nombre LIKE %:nombre% ",
            nativeQuery = true)
    List<VentaArticulo> searchNativo(@Param("nombre") String nombre);

    @Query(value = "SELECT va.id as id, a.nombre as nombre, va.cantidad_articulo as cantidadArticulo, va.sub_total as subTotal " +
            "FROM venta_articulo va " +
            "JOIN articulo a ON va.articulo_id = a.id " +
            "WHERE va.venta_id LIKE %:id%", nativeQuery = true)
    List<Map<String, Object>> todoDetalleVenta(@Param("id") Number id);

}

