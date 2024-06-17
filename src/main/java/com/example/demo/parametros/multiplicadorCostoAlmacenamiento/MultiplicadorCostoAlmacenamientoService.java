package com.example.demo.parametros.multiplicadorCostoAlmacenamiento;

import com.example.demo.servicios.BaseService;

import java.util.List;

public interface MultiplicadorCostoAlmacenamientoService extends BaseService<MultiplicadorCostoAlmacenamiento,Long> {

    List<MultiplicadorCostoAlmacenamiento> traerMCA() throws Exception;

}
