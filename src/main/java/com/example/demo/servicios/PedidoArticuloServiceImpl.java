package com.example.demo.servicios;

import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.PedidoArticulo;
import com.example.demo.entidades.ProveedorArticulo;
import com.example.demo.repositorios.ArticuloRepository;
import com.example.demo.repositorios.BaseRepository;
import com.example.demo.repositorios.OrdenDeCompraRepository;
import com.example.demo.repositorios.PedidoArticuloRepository;
import lombok.Data;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class PedidoArticuloServiceImpl extends BaseServiceImpl<PedidoArticulo,Long> implements PedidoArticuloService{


    @Autowired
    private PedidoArticuloRepository pedidoArticuloRepository;
    @Autowired
    private ArticuloRepository articuloRepository;
    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;

    public PedidoArticuloServiceImpl(BaseRepository<PedidoArticulo, Long> baseRepository, PedidoArticuloRepository pedidoArticuloRepository){
        super(baseRepository);
        this.pedidoArticuloRepository = pedidoArticuloRepository;
    }
    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public PedidoArticulo crearPedidoArticulo(int cantidad, long idArticulo, long idOrdenDeCompra) throws Exception {
        PedidoArticulo pedidoArticulo = new PedidoArticulo();
        LocalDate date = LocalDate.now();
        Date date1 = java.sql.Date.valueOf(date);
        pedidoArticulo.setArticulo(articuloRepository.getReferenceById(idArticulo));
        pedidoArticulo.setCantidad(cantidad);
        pedidoArticulo.setOrdenDeCompra(ordenDeCompraRepository.getReferenceById(idOrdenDeCompra));
        pedidoArticulo.setFechaAlta(date1);
        pedidoArticuloRepository.save(pedidoArticulo);
        return pedidoArticulo;
    }

    @Override
    public List<PedidoArticulo> search(String filtro) throws Exception {
        return null;
    }

    @Override
    public org.springframework.data.domain.Page<PedidoArticulo> search(String filtro, org.springframework.data.domain.Pageable pageable) throws Exception {
        return null;
    }
}
