package com.example.demo.parametros.multiplicadorCostoAlmacenamiento;

import com.example.demo.repositorios.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MultiplicadorCostoAlmacenamientoRepository extends BaseRepository<MultiplicadorCostoAlmacenamiento,Long> {

    @Query(value = "SELECT * FROM multiplicador_costo_almacenamiento", nativeQuery = true)
    List<MultiplicadorCostoAlmacenamiento> traerMCA();

}
