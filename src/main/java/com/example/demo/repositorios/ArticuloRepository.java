package com.example.demo.repositorios;

import com.example.demo.entidades.Articulo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticuloRepository extends BaseRepository<Articulo, Long>{


   @Query(value = "SELECT * FROM articulo", nativeQuery = true)
    List<Articulo> traerTodosArticulos();


    @Query(value = "SELECT * FROM articulo a WHERE a.nombre LIKE %:nombre% ",
            nativeQuery = true)
    List<Articulo> traerUnArticuloNombre(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM articulo a WHERE a.id = %:id% ",
            nativeQuery = true)
    Articulo traerUnArticuloId(@Param("id") Long id);
    @Query(value = "SELECT * FROM articulo a WHERE a.stock_actual <= a.stock_de_seguridad",
            nativeQuery = true)
    List<Articulo> traerArticuloBajoStock(@Param("stockDeSeguridad") int stockDeSeguridad, @Param("stockActual") int stockActual);

    @Query(value = "SELECT * FROM articulo a WHERE a.stock_actual < a.stock_de_seguridad", nativeQuery = true)
    List<Articulo> traerArticulosFaltantes(@Param("stockDeSeguridad") int stockDeSeguridad, @Param("stockActual") int stockActual);
    /*Calculo del CGI
    @Query(value = "SELECT SUM(a.precioCompra * a.stockActual) / COUNT(a.id) AS cgi " +
            "FROM articulo a " +
            "WHERE a.stockActual <= a.stockDeSeguridad", nativeQuery = true)
    Double calcularCGI();

    @Query(value = "SELECT SUM(a.precioCompra * a.stockActual) AS cgi_total FROM articulo a", nativeQuery = true)
    Double calcularCGIDeTodosArticulos();*/


}

