package com.example.demo.servicios;
import com.example.demo.entidades.DemandaHistorica;
import com.example.demo.entidades.OrdenDeCompra;
import com.example.demo.entidades.Venta;
import com.example.demo.entidades.VentaArticulo;
import com.example.demo.repositorios.*;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
@Service
public class DemandaHistoricaServiceImpl extends BaseServiceImpl<DemandaHistorica, Long> implements DemandaHistoricaService{

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private DemandaHistoricaRepository demandaHistoricaRepository;

    public DemandaHistoricaServiceImpl(BaseRepository<DemandaHistorica, Long> baseRepository, DemandaHistoricaRepository demandaHistoricaRepository) {
        super(baseRepository);
        this.demandaHistoricaRepository = demandaHistoricaRepository;
    }

    public void crearDemandaHistorica(Long idArticulo, Date fechaDesde, Date fechaHasta) throws Exception {
        //me traigo las ventas en esas fechas
        List<Venta> ventas = ventaRepository.findVentasEntreFechas(fechaDesde, fechaHasta);

        int cantVendida = 0;

        //recorro las ventas y acumula
        for (Venta venta : ventas) {
            for (VentaArticulo ventaArticulo : venta.getVentaArticulos()) {
                if(ventaArticulo.getArticulo().getId().equals(idArticulo)){
                    cantVendida= cantVendida + ventaArticulo.getCantidadArticulo();
                }
            }
        }

        //creo nueva demanda historica
        DemandaHistorica demandaHistorica = new DemandaHistorica();
        demandaHistorica.setCantidadVendida(cantVendida);
        demandaHistorica.setFechaInicio(fechaDesde);
        demandaHistorica.setFechaFin(fechaHasta);
        demandaHistorica.setFechaBaja(null);
        //demandaHistorica.setArticulo(ArticuloRepository.encontrarArticulo);
        demandaHistoricaRepository.save(demandaHistorica);

    }

    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }

}
