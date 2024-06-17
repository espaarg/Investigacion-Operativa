package com.example.demo.repositorios;

import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.DemandaHistorica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface DemandaHistoricaRepository extends BaseRepository<DemandaHistorica, Long> {

    @Query(value = "SELECT COUNT(va.cantidad_articulo) as cantidadTotal" +
            "FROM venta_articulo va " +
            "WHERE va.articulo_id == %:id%" +
            "AND fecha_baja BETWEEN %:fechaIni% AND %:fechaFin%", nativeQuery = true)
    float cantidadDemanda(@Param("id") Number id, @Param("fechaIni") String fechaIni, @Param("fechaFin") String fechaFin);

    @Query(value = "SELECT dh.id as id, a.nombre as nombreArticulo, dh.fecha_inicio as fechaInicio, dh.fecha_fin as fechaFin, dh.cantidad_vendida as cantidadVendida " +
            "FROM demanda_historica dh " +
            "JOIN articulo a ON dh.articulo_id = a.id;", nativeQuery = true)
    List<Map<String, Object>> traerTodasDemandasH();

    @Query(value = "SELECT dh.cantidadVendida " +
            "FROM DemandaHistorica dh " +
            "WHERE dh.articulo.id = :idArticulo " +
            "ORDER BY dh.fechaFin DESC " +
            "LIMIT 1", nativeQuery = false)
    Integer findCantidadVendidaMasReciente(@Param("idArticulo") Long idArticulo);
}
