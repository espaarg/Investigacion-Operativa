package com.example.demo.repositorios;


import com.example.demo.entidades.OrdenDeCompra;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdenDeCompraRepository extends BaseRepository<OrdenDeCompra, Long>{


    @Query(value = "SELECT * FROM articulo a WHERE a.nombre LIKE %:nombre% ",
            nativeQuery = true)
    List<OrdenDeCompra> searchNativo(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM orden_de_compra WHERE estado_orden_de_compra LIKE %:estado%",
            nativeQuery = true
    )
    List<OrdenDeCompra> filtrarOrdenDeCompraPorEstado(@Param("estado") String estado);

}

