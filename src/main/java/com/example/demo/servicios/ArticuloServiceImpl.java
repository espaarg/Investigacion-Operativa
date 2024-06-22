package com.example.demo.servicios;

import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.ProveedorArticulo;
import com.example.demo.enums.ModeloInventario;
import com.example.demo.parametros.multiplicadorCostoAlmacenamiento.MultiplicadorCostoAlmacenamiento;
import com.example.demo.parametros.multiplicadorCostoAlmacenamiento.MultiplicadorCostoAlmacenamientoRepository;
import com.example.demo.repositorios.ArticuloRepository;
import com.example.demo.repositorios.BaseRepository;
import com.example.demo.repositorios.ProveedorArticuloRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class ArticuloServiceImpl extends BaseServiceImpl<Articulo, Long> implements ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private MultiplicadorCostoAlmacenamientoRepository multiplicadorCostoAlmacenamientoRepository;

    @Autowired
    private ProveedorArticuloRepository proveedorArticuloRepository;

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
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void calculoLoteFijo(Long idArticulo, Long idProveedor, Long idMultiplicador) throws Exception {
        try {
            calcularCostoAlmacenamiento(idArticulo, idMultiplicador); //me da el valor pero la coma está mal ubicada quizas por el tipo de dato pero raaro
            calcularPuntoPedido(idArticulo, idProveedor); //da bien
            calcularLoteOptimo(idArticulo); //da bien
            calcularCGI(idArticulo); //nose q le pasa q a veces me da y en otra me dice q no puede dividir por 0 paraa si el lote no cero hdp
            calcularStockDeSeguridad(idArticulo); //da bien
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }
    }

    @Override
    public void calculoIntervaloFijo(Long idArticulo) throws Exception {//este anda bien enterito
        try{
            calcularCantidadMaxima(idArticulo);
            calcularCantidadAPedir(idArticulo);
            calcularStockDeSeguridad(idArticulo);
        } catch (Exception e) {
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
    public float calcularCGI(Long idArticulo) throws Exception {
        try {
            Articulo articulo = articuloRepository.findById(idArticulo)
                    .orElseThrow(() -> new Exception("Artículo no encontrado con id: " + idArticulo));
            ProveedorArticulo proveedorArticulo = articulo.getProveedorArticulo();
            float precio = articulo.getPrecioCompra();
            int demandaAnual = demandaHistoricaService.obtenerDemandaAnual(idArticulo);
            float costoAlmacenamiento = articulo.getCostoAlmacenamiento();
            int loteOptimo = articulo.getLoteOptimo();
            double costoPedido = proveedorArticulo.getCostoPedido();
            float cgi = 0;
            // Verificar si el modelo de inventario es Lote Fijo
            if (articulo.getModeloInventario() == ModeloInventario.LoteFijo) {
                cgi = (float) ((precio * demandaAnual) + (costoAlmacenamiento * (loteOptimo / 2)) + (costoPedido * (demandaAnual / loteOptimo)));
                articulo.setCgiArticulo(cgi);
                articuloRepository.save(articulo);
                return cgi;
            } else {
                throw new Exception("El modelo de inventario no es Lote Fijo. No se puede calcular el lote óptimo.");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public float calcularCostoAlmacenamiento(Long idArticulo, Long idMultiplicador) throws Exception {
        try {
            Articulo articulo = articuloRepository.findById(idArticulo)
                    .orElseThrow(() -> new Exception("Artículo no encontrado"));
            MultiplicadorCostoAlmacenamiento multiplicador = multiplicadorCostoAlmacenamientoRepository.findById(idMultiplicador)
                    .orElseThrow(() -> new Exception("Multiplicador no encontrado"));

            float costoAlmacenamiento = multiplicador.getValor() * articulo.getPrecioCompra();
            articulo.setCostoAlmacenamiento(costoAlmacenamiento);
            articuloRepository.save(articulo);

            return costoAlmacenamiento;
        } catch (Exception e) {
            throw new Exception("Error al calcular el costo de almacenamiento: " + e.getMessage());
        }
    }

    @Override
    public int calcularPuntoPedido(Long idArticulo, Long idProveedor) throws Exception {
        try {
            Articulo articulo = articuloRepository.findById(idArticulo)
                    .orElseThrow(() -> new Exception("Artículo no encontrado"));
            ProveedorArticulo proveedor = proveedorArticuloRepository.findById(idProveedor)
                    .orElseThrow(() -> new Exception("Proveedor no encontrado"));
            // Verificar si el modelo de inventario es Lote Fijo
            if (articulo.getModeloInventario() == ModeloInventario.LoteFijo) {
                // Obtener la demanda anual desde DemandaHistoricaService
                int demandaAnual = demandaHistoricaService.obtenerDemandaAnual(idArticulo);
                double demandaDiaria = demandaAnual/365; //consideramos que trabajamos 365 dias al año
                int puntoPedido = (int) demandaDiaria * proveedor.getDiasDemora();
                articulo.setPuntoPedido(puntoPedido);
                articuloRepository.save(articulo);

                return puntoPedido;
            } else {
                throw new Exception("El modelo de inventario no es Lote Fijo. No se puede calcular el lote óptimo.");
            }
        } catch (Exception e) {
            throw new Exception("Error al calcular el punto de pedido: " + e.getMessage());
        }
    }

    @Override
    public int calcularStockDeSeguridad(Long idArticulo) throws Exception {
        try{
            Articulo articulo = articuloRepository.findById(idArticulo)
                    .orElseThrow(() -> new Exception("Artículo no encontrado"));
            ProveedorArticulo proveedorArticulo = proveedorArticuloRepository.findById(articulo.getProveedorArticulo().getId())
                    .orElseThrow(() -> new Exception("Proveedor no encontrado"));
            //definimos un z=1,67
            double Z = 1.67;
            double desvEstandarD = 1.0; //el profe dijo que a la desviacion estandar de la demanda sea 1
            int diasDemora = proveedorArticulo.getDiasDemora();
            int tiempoEntrePedidos = articulo.getTiempoEntrePedidos();
            int stockSeguridad = 0;
            if (articulo.getModeloInventario() == ModeloInventario.LoteFijo) {
                stockSeguridad= (int) (Z * desvEstandarD * Math.sqrt(diasDemora));
                articulo.setStockDeSeguridad(stockSeguridad);
            } else if (articulo.getModeloInventario() == ModeloInventario.IntervaloFijo) {
                stockSeguridad=(int) (Z * desvEstandarD * Math.sqrt(tiempoEntrePedidos + diasDemora));
                articulo.setStockDeSeguridad(stockSeguridad);
            }
            articuloRepository.save(articulo);
            return stockSeguridad;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public int calcularCantidadMaxima(Long idArticulo) throws Exception {
        try{
            Articulo articulo = articuloRepository.findById(idArticulo)
                    .orElseThrow(() -> new Exception("Artículo no encontrado"));
            ProveedorArticulo proveedorArticulo = proveedorArticuloRepository.findById(articulo.getProveedorArticulo().getId())
                    .orElseThrow(() -> new Exception("Proveedor no encontrado"));
            int diasDemora = proveedorArticulo.getDiasDemora();
            int tiempoEntrePedidos = articulo.getTiempoEntrePedidos();
            // Obtener la demanda anual desde DemandaHistoricaService
            int demandaAnual = demandaHistoricaService.obtenerDemandaAnual(idArticulo);
            int ss = articulo.getStockDeSeguridad();
            int cantMax = 0;
            float demandaDiaria = (float) demandaAnual /365; //consideramos que trabajamos 365 dias al año
            if (articulo.getModeloInventario() == ModeloInventario.IntervaloFijo) {
                cantMax = (int) (demandaDiaria * (tiempoEntrePedidos+diasDemora) + ss); //me da 2 numeritos menos de lo me debería dar
                //antes el tipo de dato de demanda diaria era double y eran como 11 numeritos menos pero nose q tipo de dato ponerle para q se acerque más :(
                articulo.setCantMax(cantMax);
            } else {
                throw new Exception("El modelo de inventario no es Intervalo Fijo. No se puede calcular la cantidad máxima.");
            }
            articuloRepository.save(articulo);
            return cantMax;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public int calcularCantidadAPedir(Long idArticulo) throws Exception {
        try{
            Articulo articulo = articuloRepository.findById(idArticulo)
                    .orElseThrow(() -> new Exception("Artículo no encontrado"));
            int inventario = articulo.getStockActual();
            int cantMax = articulo.getCantMax();
            int cantAPedir = 0;
            if (articulo.getModeloInventario() == ModeloInventario.IntervaloFijo) {
                cantAPedir = (cantMax-inventario);
                articulo.setCantAPedir(cantAPedir);
            } else {
                throw new Exception("El modelo de inventario no es Intervalo Fijo. No se puede calcular la cantidad a pedir.");
            }
            articuloRepository.save(articulo);
            return cantAPedir;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public List<Articulo> obtenerArticulosParaReorden() throws Exception {
        try {
            List<Articulo> articulos = articuloRepository.findAll();
            List<Articulo> articulosParaReorden = new ArrayList<>();

            for (Articulo articulo : articulos) {
                if (articulo.getStockActual() <= articulo.getPuntoPedido() &&
                        !ordenDeCompraService.buscarOrdenesActivas(articulo)) {
                    articulosParaReorden.add(articulo);
                }
            }

            return articulosParaReorden;
        } catch (Exception e) {
            throw new Exception("Error al obtener los artículos para reorden: " + e.getMessage());
        }
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
