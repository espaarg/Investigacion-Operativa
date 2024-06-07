package com.example.demo.repositorios;

import com.example.demo.entidades.PedidoArticulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoArticuloRepository extends BaseRepository<PedidoArticulo, Long> {

    /*@Query(
            value = "SELECT * FROM pedidoArticulo WHERE pedidoArticulo.cantidad LIKE %:cantidad%", nativeQuery = true
    )
    List<PedidoArticulo> searchNativo(@Param("cantidad") String cantidad);
    Page<PedidoArticulo> searchNativo(@Param("cantidad") String cantidad, Pageable pageable);

     */
}
