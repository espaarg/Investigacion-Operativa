package com.example.demo.repositorios;

import com.example.demo.entidades.VentaArticulo;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VentaArticuloRepository extends BaseRepository<VentaArticulo, Long>{


    @Query(value = "SELECT * FROM articulo a WHERE a.nombre LIKE %:nombre% ",
            nativeQuery = true)
    List<VentaArticulo> searchNativo(@Param("nombre") String nombre);

}

