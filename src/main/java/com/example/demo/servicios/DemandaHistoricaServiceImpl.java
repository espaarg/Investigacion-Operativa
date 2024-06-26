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
    @Autowired
    private ArticuloServiceImpl articuloServiceImpl;

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
        articuloServiceImpl.calcularCGI(idArticulo);

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

    //trae la mas reciente
    public Integer obtenerDemandaAnual(Long idArticulo) throws Exception {
        try {
            Integer demanda = demandaHistoricaRepository.findCantidadVendidaMasReciente(idArticulo);
            if(demanda == null){
                return 0;
            }else{
                return demanda;
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener la cantidad vendida más reciente: " + e.getMessage());
        }
    }

    //trae la de una fecha especifica
    public Integer buscarDemandaAnual(Long idArticulo, Date fechaDesde, Date fechaHasta) throws Exception {
        try {
            Integer demanda= demandaHistoricaRepository.findDemanda(idArticulo, fechaDesde, fechaHasta);
            if(demanda == null){
                return 0;
            }else{
                return demanda;
            }

        } catch (Exception e) {
            throw new Exception("Error al obtener demanda: " + e.getMessage());
        }
    }

    @Override
    public Integer calcularDemandaHistoricaArticulo(Long idArticulo, String fechaDesde, String fechaHasta) {
        System.out.println("ID de Artículo recibido en calcularDemandaHistoricaArticulo: " + idArticulo);
        List<Venta> ventas = ventaRepository.findVentasEntreFechas(fechaDesde, fechaHasta);
        boolean existe = false;
        int cantidadTotalVendida = 0;
        //recorrer ventas y acumular la cantidad vendida del articulo
        for (Venta venta : ventas) {
            for (VentaArticulo cantidadArticulo : venta.getVentaArticulos()) {
                if (cantidadArticulo.getArticulo().getId().equals(idArticulo)) {
                    cantidadTotalVendida = cantidadTotalVendida + cantidadArticulo.getCantidadArticulo();
                    existe = true;
                }
            }
        }
       return cantidadTotalVendida;
    }
    @Override
    public Integer calcularDemandaHistorica(Long idArticulo, String fechaDesde, String fechaHasta) {
        int cantidadTotal = calcularDemandaHistoricaArticulo(idArticulo, fechaDesde, fechaHasta);
        return cantidadTotal;
    }

}
