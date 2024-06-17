package com.example.demo.servicios;
import com.example.demo.dtos.DemandaHistoricaDTO;
import com.example.demo.dtos.VentaArticuloDTO;
import com.example.demo.entidades.*;
import com.example.demo.repositorios.*;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void crearDemandaHistorica(Long idArticulo, String fechaDesde, String fechaHasta) throws Exception {
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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date desde = formatter.parse(fechaDesde);
        Date hasta = formatter.parse(fechaHasta);

        //creo nueva demanda historica
        DemandaHistorica demandaHistorica = new DemandaHistorica();
        demandaHistorica.setCantidadVendida(cantVendida);
        demandaHistorica.setFechaInicio(desde);
        demandaHistorica.setFechaFin(hasta);
        demandaHistorica.setFechaBaja(null);
        demandaHistorica.setArticulo(articuloRepository.traerUnArticuloId(idArticulo));
        demandaHistoricaRepository.save(demandaHistorica);

    }

    @Override
    public void crearDemandaH(Long idArticulo, String fechaIni, String fechaFin) throws Exception {

        float cantVendida = demandaHistoricaRepository.cantidadDemanda(idArticulo, fechaIni, fechaFin);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date desde = formatter.parse(fechaIni);
        Date hasta = formatter.parse(fechaFin);

        DemandaHistorica demandaHistorica = new DemandaHistorica();
        demandaHistorica.setCantidadVendida((int) cantVendida);
        demandaHistorica.setFechaInicio(desde);
        demandaHistorica.setFechaFin(hasta);
        demandaHistorica.setFechaBaja(null);
        demandaHistorica.setArticulo(articuloRepository.traerUnArticuloId(idArticulo));
        demandaHistoricaRepository.save(demandaHistorica);

    }

    @Override
    public List<DemandaHistoricaDTO> traerTodasDemandasH() throws Exception {
        List<DemandaHistoricaDTO> dHList;
        List<Map<String, Object>> demandasH = demandaHistoricaRepository.traerTodasDemandasH();

        dHList = demandasH.stream()
                .map(result->new DemandaHistoricaDTO(
                        ((long) result.get("id")),
                        ((int) result.get("cantidadVendida")),
                        (String) result.get("fechaFin").toString(),
                        ((String) result.get("fechaInicio").toString()),
                        ((String) result.get("nombreArticulo"))))
                .collect(Collectors.toList());

        return dHList;
    }

    @Override
    public void eliminarDemandaHistorica(Long id) throws Exception {
        demandaHistoricaRepository.deleteById(id);
    }

    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }

    public Integer obtenerDemandaAnual(Long idArticulo) throws Exception {
        try {
            return demandaHistoricaRepository.findCantidadVendidaMasReciente(idArticulo);
        } catch (Exception e) {
            throw new Exception("Error al obtener la cantidad vendida más reciente: " + e.getMessage());
        }
    }


}
