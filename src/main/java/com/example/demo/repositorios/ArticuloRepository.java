package com.example.demo.repositorios;

import com.example.demo.entidades.Articulo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ArticuloRepository extends BaseRepository<Articulo, Long>{


   @Query(value = "SELECT a.id as id, a.nombre as nombre, a.precio_compra as precioCompra, a.stock_actual as stockActual, a.stock_de_seguridad as stockDeSeguridad, a.cantapedir as cantAPedir, a.cant_max as cantMax, a.cgi_articulo as cgiArticulo, a.costo_almacenamiento as costoAlmacenamiento, a.fecha_alta as fechaAlta, a.lote_optimo as loteOptimo, a.modelo_inventario as modeloInventario, a.punto_pedido as puntoPedido, a.tiempo_entre_pedidos as tiempoEntrePedidos, p.nombre_proveedor as proveedorArticulo " +
                  "FROM articulo a " +
                  "JOIN proveedor_articulo p ON a.proveedor_articulo_id = p.id;", nativeQuery = true)
   List<Map<String, Object>> traerTodosArticulos();


    @Query(value = "SELECT * FROM articulo a WHERE a.nombre LIKE %:nombre% ",
            nativeQuery = true)
    List<Articulo> traerUnArticuloNombre(@Param("nombre") String nombre);

   @Query("SELECT a.proveedorArticulo.id FROM Articulo a WHERE a.nombre = :nombre")
   Long traerIdProveedor(@Param("nombre") String nombre);

    @Query(value = "SELECT a.id FROM articulo a WHERE a.nombre LIKE %:nombre% ",
            nativeQuery = true)
    Long traerUnIdArticuloNombre(@Param("nombre") String nombre);

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

