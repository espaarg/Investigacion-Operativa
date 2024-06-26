package com.example.demo.repositorios;

import com.example.demo.entidades.PrediccionDemanda;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrediccionDemandaRepository extends BaseRepository<PrediccionDemanda, Long> {

    @Query(value = "SELECT * FROM prediccion_demanda ",
    nativeQuery = true)
    List<PrediccionDemanda> traerTodasPredicciones();
}
