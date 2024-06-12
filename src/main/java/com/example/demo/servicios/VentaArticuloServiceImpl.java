package com.example.demo.servicios;


import com.example.demo.dtos.VentaArticuloDTO;
import com.example.demo.entidades.VentaArticulo;
import com.example.demo.repositorios.BaseRepository;
import com.example.demo.repositorios.VentaArticuloRepository;
import jakarta.persistence.Tuple;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class VentaArticuloServiceImpl extends BaseServiceImpl<VentaArticulo, Long> implements VentaArticuloService{

    @Autowired
    private VentaArticuloRepository ventaArticuloRepository;

    public VentaArticuloServiceImpl(BaseRepository<VentaArticulo, Long> baseRepository, VentaArticuloRepository ventaArticuloRepository) {
        super(baseRepository);
        this.ventaArticuloRepository = ventaArticuloRepository;
    }

    @Override
    public List<VentaArticuloDTO> todoDetalleVenta(Long id) throws Exception {
        try {
            List<Map<String, Object>> ventaArticulos = ventaArticuloRepository.todoDetalleVenta(id);
            return ventaArticulos.stream()
                    .map(result->new VentaArticuloDTO(
                            ((Number) result.get("id")).longValue(),
                            (String) result.get("nombre"),
                            ((Number) result.get("cantidadArticulo")).intValue(),
                            ((Number) result.get("subTotal")).floatValue()))
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<VentaArticulo> search(String nombre) throws Exception {
        try {
            List<VentaArticulo> ventaArticulos = ventaArticuloRepository.searchNativo(nombre);
            return ventaArticulos;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }


}