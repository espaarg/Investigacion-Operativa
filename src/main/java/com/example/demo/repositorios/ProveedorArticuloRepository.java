package com.example.demo.repositorios;

import com.example.demo.entidades.ProveedorArticulo;

public interface ProveedorArticuloRepository extends BaseRepository<ProveedorArticulo, Long>{
    /*@Query(
            value = "SELECT * FROM proveedorArticulo WHERE proveedorArticulo.cantidad LIKE %:codigo%", nativeQuery = true
    )
    List<ProveedorArticulo> searchNativo(@Param("codigo") String codigo);
    Page<ProveedorArticulo> searchNativo(@Param("codigo") String codigo, Pageable pageable);

     */
}
