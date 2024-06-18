package com.example.demo.servicios;

import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.ProveedorArticulo;
import com.example.demo.enums.ModeloInventario;
import com.example.demo.repositorios.ArticuloRepository;
import com.example.demo.repositorios.BaseRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

@Service

public class ArticuloServiceImpl extends BaseServiceImpl<Articulo, Long> implements ArticuloService{

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private DemandaHistoricaService demandaHistoricaService;

    @Autowired
    private OrdenDeCompraService ordenDeCompraService;

    public ArticuloServiceImpl(BaseRepository<Articulo, Long> baseRepository, ArticuloRepository articuloRepository) {
        super(baseRepository);
        this.articuloRepository = articuloRepository;
    }

    @Override
    public List<Articulo> traerTodosArticulos() throws Exception {
        try {
            List<Articulo> articulos = articuloRepository.traerTodosArticulos();
            return articulos;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



    @Override
    public double calcularLoteOptimo(Long idArticulo) throws Exception {
        try {
            Articulo articulo = articuloRepository.findById(idArticulo)
                    .orElseThrow(() -> new Exception("Artículo no encontrado con id: " + idArticulo));
            ProveedorArticulo proveedorArticulo = articulo.getProveedorArticulo();
            // Verificar si el modelo de inventario es Lote Fijo
            if (articulo.getModeloInventario() == ModeloInventario.LoteFijo) {
                double demandaAnual = demandaHistoricaService.obtenerDemandaAnual(idArticulo);
                double eoq = Math.sqrt((2 * proveedorArticulo.getCostoPedido() * demandaAnual) / articulo.getCostoAlmacenamiento());
                articulo.setLoteOptimo((int) eoq);
                articuloRepository.save(articulo);
                return eoq;
            } else {
                throw new Exception("El modelo de inventario no es Lote Fijo. No se puede calcular el lote óptimo.");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public double calcularCGI() throws Exception {
        return 0;
    }
    /*@Override
    public double calcularCGI(int stockActual, float precioCompra) throws Exception {
        if (stockActual <= 0 || precioCompra <= 0) {
            throw new IllegalArgumentException("Stock actual y precio de compra deben ser mayores que cero");
        }
        return precioCompra * stockActual;
    }
    @Override
    public List<Double> calcularCGIDeTodosArticulos() throws Exception {
        List<Articulo> articulos = articuloRepository.traerTodosArticulos();
        List<Double> cgis = new ArrayList<>();

        for (Articulo articulo : articulos) {
            double cgi = calcularCGI(articulo.getStockActual(), articulo.getPrecioCompra());
            cgis.add(cgi);
        }

        return cgis;
    }*/



    /*public int calcularStockSeguridad(int puntoPedido, int demoraProveedor) {
        demoraProveedor = ProveedorArticulo.getDiasDemora();
        int stockDeSeguridad = puntoPedido * demoraProveedor;
        return this.stockDeSeguridad = stockDeSeguridad;
    }
*/

/*    @Override
    public List<BusquedaArticulosDTO> traerTodosArticulos() throws Exception {
        try {
            List<ArticuloInsumo> articuloInsumos = articuloInsumoRepository.controlStockInsuficiente();
            List<ControlStockDTO> stockDTO = new ArrayList<>();

            for (ArticuloInsumo articulos : articuloInsumos) {
                // Verifica si el stock actual es menor que el stock mínimo
                if (articulos.getStockActual() < articulos.getStockMinimo()) {
                    ControlStockDTO auxDTO = new ControlStockDTO();
                    auxDTO.setNombre(articulos.getDenominacion());
                    auxDTO.setDenominacion(articulos.getUnidadMedida().getDenominacion());
                    auxDTO.setAbreviatura(articulos.getUnidadMedida().getAbreviatura());
                    auxDTO.setStockMinimo(articulos.getStockMinimo());
                    auxDTO.setStockActual(articulos.getStockActual());
                    auxDTO.setDiferenciaStock(articulos.getStockActual() - articulos.getStockMinimo());

                    stockDTO.add(auxDTO);
                }
            }
            return stockDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }*/


/*    @Override
    public List<ControlStockDTO> controlStockBajo() throws Exception {
        try {
            List<ArticuloInsumo> articuloInsumos = articuloInsumoRepository.controlStockInsuficiente();
            List<ControlStockDTO> stockDTO = new ArrayList<>();

            for (ArticuloInsumo articulos : articuloInsumos) {
                double porcentajeUmbral = 20.0;

                double diferenciaPorcentaje = ((double) articulos.getStockActual() / articulos.getStockMinimo() - 1) * 100;

                // Verifica si la diferencia porcentual es menor o igual al 20%
                // y si el stock actual no está por debajo del stock mínimo
                if (diferenciaPorcentaje <= porcentajeUmbral && articulos.getStockActual() >= articulos.getStockMinimo()) {
                    ControlStockDTO auxDTO = new ControlStockDTO();
                    auxDTO.setNombre(articulos.getDenominacion());
                    auxDTO.setDenominacion(articulos.getUnidadMedida().getDenominacion());
                    auxDTO.setAbreviatura(articulos.getUnidadMedida().getAbreviatura());
                    auxDTO.setStockMinimo(articulos.getStockMinimo());
                    auxDTO.setStockActual(articulos.getStockActual());
                    auxDTO.setDiferenciaStock(articulos.getStockActual() - articulos.getStockMinimo());

                    stockDTO.add(auxDTO);
                }
            }
            return stockDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }*/



    @Override
    public List<Articulo> traerUnArticuloNombre(String nombre) throws Exception {
        try {
            List<Articulo> articulo = articuloRepository.traerUnArticuloNombre(nombre);
            return articulo;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Override
    public Articulo traerUnArticuloId(Long id) throws ChangeSetPersister.NotFoundException {
        // Assuming NotFoundException is a custom exception for indicating that an entity was not found
        Articulo articulo = articuloRepository.traerUnArticuloId(id);
        if (articulo == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return articulo;
    }
    @Override
    public List<Articulo> traerArticuloBajoStock(int stockDeSeguridad, int stockActual) throws Exception {
        try {
            List<Articulo> articulo = articuloRepository.traerArticuloBajoStock(stockDeSeguridad, stockActual);
            return articulo;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Override
    @Transactional(readOnly = true)
    public List<Articulo> traerArticulosFaltantes(int stockDeSeguridad, int stockActual) throws Exception {
        try {List<Articulo> articulo = articuloRepository.traerArticulosFaltantes(stockDeSeguridad, stockActual);
            return articulo;
        } catch (Exception e) {
            throw new Exception("Error al traer los artículos faltantes: " + e.getMessage());
        }
    }

    public void darDeBajaArticulo(Articulo articulo) throws Exception {
        if (ordenDeCompraService.buscarOrdenesActivas(articulo)) {
            throw new Exception("No se puede dar de baja el artículo porque existen órdenes activas.");
        }

        try {
            articulo.setFechaBaja(new Date());
            articuloRepository.save(articulo);
        } catch (Exception e) {
            throw new Exception("Error al dar de baja el artículo: " + e.getMessage(), e);
        }
    }


    /*@Override
    public double calcularCGI() throws Exception {
        return articuloRepository.calcularCGIDeTodosArticulos();
    }*/

    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }

/*    @Override
    public Page<ArticuloInsumo> search(String denominacion, Number min, Number max, Number stockMenor, Number minStock, Number maxStock, Pageable pageable) throws Exception {
        try {
            Page<ArticuloInsumo> articuloInsumos = articuloInsumoRepository.searchNativo(denominacion,min,max,stockMenor,minStock,maxStock, pageable);
            return articuloInsumos;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }*/
}
