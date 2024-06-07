package com.example.demo.servicios;

import com.example.demo.entidades.PedidoArticulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PedidoArticuloService extends BaseService<PedidoArticulo, Long>{
   /* PedidoArticulo cambiarEstado(DTOCambiarEstado cambiarEstadoDTO) throws Exception;
   */
   List<PedidoArticulo> search(String filtro) throws Exception;
   Page<PedidoArticulo> search(String filtro, Pageable pageable) throws Exception;


}
