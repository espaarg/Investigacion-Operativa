package com.example.demo.repositorios;

import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.ProveedorArticulo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProveedorArticuloRepository extends BaseRepository<ProveedorArticulo, Long>{

    @Query(value = "SELECT * FROM proveedor_articulo", nativeQuery = true)
    List<ProveedorArticulo> traerTodosProveedores();



    /*@Query(
            value = "SELECT * FROM proveedorArticulo WHERE proveedorArticulo.cantidad LIKE %:codigo%", nativeQuery = true
    )
    List<ProveedorArticulo> searchNativo(@Param("codigo") String codigo);
    Page<ProveedorArticulo> searchNativo(@Param("codigo") String codigo, Pageable pageable);

     */
}
