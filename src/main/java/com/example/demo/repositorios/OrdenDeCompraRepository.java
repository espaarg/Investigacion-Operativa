package com.example.demo.repositorios;


import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.OrdenDeCompra;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface OrdenDeCompraRepository extends BaseRepository<OrdenDeCompra, Long>{

    @Query(value = "SELECT oc.id as id, oc.total_compra as totalCompra, oc.cantidad as cantidad, oc.precio_individual as precioIndividual, oc.fecha_pedido as fechaPedido, oc.fecha_llegada as fechaLlegada, oc.estado_orden_de_compra as estado, a.nombre as nombreArticulo, p.nombre_proveedor as proveedorArticulo " +
                  "FROM orden_de_compra oc " +
                  "JOIN articulo a ON oc.articulo_id = a.id " +
                  "JOIN proveedor_articulo p ON oc.proveedor_articulo_id = p.id", nativeQuery = true)
    List<Map<String, Object>> traerTodasOrdenes();

    @Query(value = "SELECT * FROM articulo a WHERE a.nombre LIKE %:nombre% ",
            nativeQuery = true)
    List<OrdenDeCompra> searchNativo(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM orden_de_compra WHERE estado_orden_de_compra LIKE %:estado%",
            nativeQuery = true
    )
    List<OrdenDeCompra> filtrarOrdenDeCompraPorEstado(@Param("estado") String estado);

}

