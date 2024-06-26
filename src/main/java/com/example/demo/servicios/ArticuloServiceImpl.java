package com.example.demo.servicios;

import com.example.demo.dtos.ArticuloDTO;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public List<ArticuloDTO> traerTodosArticulos() throws Exception {
        try {
            List<ArticuloDTO> articuloDTOS;
            List<Map<String, Object>> articulos = articuloRepository.traerTodosArticulos();
            articuloDTOS = articulos.stream()
                    .map(result->new ArticuloDTO(
                            result.get("cantAPedir") != null ? ((Number) result.get("cantAPedir")).intValue() : 0,
                            result.get("cantMax") != null ? ((Number) result.get("cantMax")).intValue() : 0,
                            result.get("cgiArticulo") != null ? ((Number) result.get("cgiArticulo")).floatValue() : 0,
                            result.get("costoAlmacenamiento") != null ? ((Number) result.get("costoAlmacenamiento")).longValue() : 0L,
                            result.get("fechaAlta") != null ? (String) result.get("fechaAlta").toString() : "",
                            result.get("id") != null ? ((Number) result.get("id")).longValue() : 0L,
                            result.get("loteOptimo") != null ? ((Number) result.get("loteOptimo")).intValue() : 0,
                            result.get("modeloInventario") != null ? (String) result.get("modeloInventario").toString() : "",
                            result.get("nombre") != null ? (String) result.get("nombre") : "",
                            result.get("precioCompra") != null ? ((Number) result.get("precioCompra")).longValue() : 0L,
                            result.get("proveedorArticulo") != null ? (String) result.get("proveedorArticulo").toString() : "",
                            result.get("puntoPedido") != null ? ((Number) result.get("puntoPedido")).intValue() : 0,
                            result.get("stockActual") != null ? ((Number) result.get("stockActual")).intValue() : 0,
                            result.get("stockDeSeguridad") != null ? ((Number) result.get("stockDeSeguridad")).intValue() : 0,
                            result.get("tiempoEntrePedidos") != null ? ((Number) result.get("tiempoEntrePedidos")).intValue() : 0))

                     .collect(Collectors.toList());

            return articuloDTOS;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void actualizarValores() throws Exception {
        List<ArticuloDTO> articuloDTOS = traerTodosArticulos();
        MultiplicadorCostoAlmacenamiento multiplicador = multiplicadorCostoAlmacenamientoRepository.getReferenceById(1L);
        for (ArticuloDTO articulo : articuloDTOS){
            if (Objects.equals(articulo.getModeloInventario(), ModeloInventario.LOTEFIJO.toString())){
                String nombre = articulo.getNombre();
                calculoLoteFijo(articuloRepository.traerUnIdArticuloNombre(nombre) ,articuloRepository.traerIdProveedor(nombre), multiplicador.getId());
            }else if (Objects.equals(articulo.getModeloInventario(), ModeloInventario.INTERVALOFIJO.toString())){
                String nombre = articulo.getNombre();
                calculoIntervaloFijo(articuloRepository.traerUnIdArticuloNombre(nombre));
            }
        }

    }

    @Override
    public void calculoLoteFijo(Long idArticulo, Long idProveedor, Long idMultiplicador) throws Exception {
        calcularCGI(idArticulo); //nose q le pasa q a veces me da y en otra me dice q no puede dividir por 0 paraa si el lote no cero hdp
        calcularLoteOptimo(idArticulo); //da bien
        calcularCostoAlmacenamiento(idArticulo, idMultiplicador); //me da el valor pero la coma está mal ubicada quizas por el tipo de dato pero raaro
        calcularPuntoPedido(idArticulo, idProveedor); //da bien
        calcularStockDeSeguridad(idArticulo); //da bien
    }

    @Override
    public void calculoIntervaloFijo(Long idArticulo) throws Exception {//este anda bien enterito
        calcularCantidadMaxima(idArticulo);
        calcularCantidadAPedir(idArticulo);
        calcularStockDeSeguridad(idArticulo);
    }

    @Override
    public void crearArticulo(String nombre, long precioCompra, int stockDeSeguridad, int stockActual, int loteOptimo, int cantMax, String modeloInventario, String proveedorArticulo, Float cgiArticulo, int cantAPedir, int puntoPedido, int tiempoEntrePedidos) throws Exception {
        Articulo articulo = new Articulo();
        LocalDate date = LocalDate.now();
        Date date1 = java.sql.Date.valueOf(date);

        articulo.setProveedorArticulo(proveedorArticuloRepository.traerProveedorPorNombre(proveedorArticulo));
        articulo.setPrecioCompra(precioCompra);
        articulo.setCantMax(cantMax);
        articulo.setFechaAlta(date1);
        articulo.setModeloInventario(ModeloInventario.valueOf(modeloInventario.toUpperCase()));
        articulo.setStockActual(stockActual);
        articulo.setStockDeSeguridad(stockDeSeguridad);
        articulo.setCantAPedir(cantAPedir);
        articulo.setNombre(nombre);
        articulo.setLoteOptimo(loteOptimo);
        articulo.setCgiArticulo(cgiArticulo);
        articulo.setPuntoPedido(puntoPedido);
        articulo.setTiempoEntrePedidos(tiempoEntrePedidos);
        articulo = articuloRepository.save(articulo);
        articulo.setCostoAlmacenamiento(calcularCostoAlmacenamiento(articulo.getId(), 1L));
        articuloRepository.save(articulo);
    }

    @Override
    public void actualizarArticulo( Long id,
                                    String nombre,
                                    Float precioCompra,
                                    Integer stockActual,
                                    Integer stockDeSeguridad,
                                    Integer loteOptimo,
                                    Integer cantMax,
                                    String modeloInventario,
                                    String proveedorArticulo,
                                    Float cgiArticulo,
                                    Integer cantAPedir,
                                    Integer puntoPedido,
                                    Integer tiempoEntrePedidos) throws Exception {

        Articulo articulo = articuloRepository.getReferenceById(id);

        if (proveedorArticulo != null) {
            articulo.setProveedorArticulo(proveedorArticuloRepository.traerProveedorPorNombre(proveedorArticulo));
        }
        if (precioCompra != null) {
            articulo.setPrecioCompra(precioCompra);
        }
        if (cantMax != null) {
            articulo.setCantMax(cantMax);
        }
        if (modeloInventario != null) {
            articulo.setModeloInventario(ModeloInventario.valueOf(modeloInventario.toUpperCase()));
        }
        if (stockActual != null) {
            articulo.setStockActual(stockActual);
        }
        if (stockDeSeguridad != null) {
            articulo.setStockDeSeguridad(stockDeSeguridad);
        }
        if (cantAPedir != null) {
            articulo.setCantAPedir(cantAPedir);
        }
        if (nombre != null) {
            articulo.setNombre(nombre);
        }
        if (loteOptimo != null) {
            articulo.setLoteOptimo(loteOptimo);
        }
        if (cgiArticulo != null) {
            articulo.setCgiArticulo(cgiArticulo);
        }
        if (puntoPedido != null) {
            articulo.setPuntoPedido(puntoPedido);
        }
        if (tiempoEntrePedidos != null) {
            articulo.setTiempoEntrePedidos(tiempoEntrePedidos);
        }

        articulo.setCostoAlmacenamiento(calcularCostoAlmacenamiento(articulo.getId(), 1L));
        articuloRepository.save(articulo);
    }

    @Override
    public double calcularLoteOptimo(Long idArticulo) throws Exception {
        try {
            Articulo articulo = articuloRepository.findById(idArticulo)
                    .orElseThrow(() -> new Exception("Artículo no encontrado con id: " + idArticulo));
            ProveedorArticulo proveedorArticulo = articulo.getProveedorArticulo();
            // Verificar si el modelo de inventario es Lote Fijo
            if (articulo.getModeloInventario() == ModeloInventario.LOTEFIJO) {
                double demandaAnual = (double) demandaHistoricaService.obtenerDemandaAnual(idArticulo);
                double eoq = Math.sqrt((2 * proveedorArticulo.getCostoPedido() * demandaAnual) / articulo.getCostoAlmacenamiento());
                articulo.setLoteOptimo((int)eoq);
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
    public double calcularCGI(Long idArticulo) throws Exception {
        try {
            Articulo articulo = articuloRepository.findById(idArticulo)
                    .orElseThrow(() -> new Exception("Artículo no encontrado con id: " + idArticulo));
            ProveedorArticulo proveedorArticulo = articulo.getProveedorArticulo();
            float precio = articulo.getPrecioCompra();
            int demandaAnual = (int) demandaHistoricaService.obtenerDemandaAnual(idArticulo);
            float costoAlmacenamiento = articulo.getCostoAlmacenamiento();
            int loteOptimo = articulo.getLoteOptimo();
            double costoPedido = proveedorArticulo.getCostoPedido();
            double cgi = 0;
            // Verificar si el modelo de inventario es Lote Fijo
            if (articulo.getModeloInventario() == ModeloInventario.LOTEFIJO) {
                cgi = (double) ((precio * demandaAnual) + (costoAlmacenamiento * ((double) loteOptimo / 2)) + (costoPedido * ((double) demandaAnual / loteOptimo)));
                articulo.setCgiArticulo(cgi);
                articuloRepository.save(articulo);
                return cgi;
            } else {
                throw new Exception("El modelo de inventario no es Lote Fijo. No se puede calcular el cgi.");
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
            if (articulo.getModeloInventario() == ModeloInventario.LOTEFIJO) {
                // Obtener la demanda anual desde DemandaHistoricaService
                int demandaAnual = (int) demandaHistoricaService.obtenerDemandaAnual(idArticulo);
                double demandaDiaria = (double) demandaAnual /365; //consideramos que trabajamos 365 dias al año
                int puntoPedido = (int) demandaDiaria * proveedor.getDiasDemora();
                articulo.setPuntoPedido(puntoPedido);
                articuloRepository.save(articulo);

                return puntoPedido;
            } else {
                throw new Exception("El modelo de inventario no es Lote Fijo. No se puede calcular el punto de pedido.");
            }
        } catch (Exception e) {
            throw new Exception("Error al calcular el punto de pedido: " + e.getMessage());
        }
    }

    @Override
    public int calcularStockDeSeguridad(Long idArticulo) throws Exception {
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
        if (articulo.getModeloInventario() == ModeloInventario.LOTEFIJO) {
            stockSeguridad= (int) (Z * desvEstandarD * Math.sqrt(diasDemora));
            articulo.setStockDeSeguridad(stockSeguridad);
        } else if (articulo.getModeloInventario() == ModeloInventario.INTERVALOFIJO) {
            stockSeguridad=(int) (Z * desvEstandarD * Math.sqrt(tiempoEntrePedidos + diasDemora));
            articulo.setStockDeSeguridad(stockSeguridad);
        }
        articuloRepository.save(articulo);
        return stockSeguridad;
    }

    @Override
    public int calcularCantidadMaxima(Long idArticulo) throws Exception {
        Articulo articulo = articuloRepository.findById(idArticulo)
                .orElseThrow(() -> new Exception("Artículo no encontrado"));
        ProveedorArticulo proveedorArticulo = proveedorArticuloRepository.findById(articulo.getProveedorArticulo().getId())
                .orElseThrow(() -> new Exception("Proveedor no encontrado"));
        int diasDemora = proveedorArticulo.getDiasDemora();
        int tiempoEntrePedidos = articulo.getTiempoEntrePedidos();
        // Obtener la demanda anual desde DemandaHistoricaService
        int demandaAnual = (int) demandaHistoricaService.obtenerDemandaAnual(idArticulo);
        int ss = articulo.getStockDeSeguridad();
        int cantMax = 0;
        float demandaDiaria = (float) demandaAnual /365; //consideramos que trabajamos 365 dias al año
        if (articulo.getModeloInventario() == ModeloInventario.INTERVALOFIJO) {
            cantMax = (int) (demandaDiaria * (tiempoEntrePedidos+diasDemora) + ss); //me da 2 numeritos menos de lo me debería dar
            //antes el tipo de dato de demanda diaria era double y eran como 11 numeritos menos pero nose q tipo de dato ponerle para q se acerque más :(
            articulo.setCantMax(cantMax);
        } else {
            throw new Exception("El modelo de inventario no es Intervalo Fijo. No se puede calcular la cantidad máxima.");
        }
        articuloRepository.save(articulo);
        return cantMax;

    }

    @Override
    public int calcularCantidadAPedir(Long idArticulo) throws Exception {
        Articulo articulo = articuloRepository.findById(idArticulo)
                .orElseThrow(() -> new Exception("Artículo no encontrado"));
        int inventario = articulo.getStockActual();
        int cantMax = articulo.getCantMax();
        int cantAPedir = 0;
        if (articulo.getModeloInventario() == ModeloInventario.INTERVALOFIJO) {
            cantAPedir = (cantMax-inventario);
            articulo.setCantAPedir(cantAPedir);
        } else {
            throw new Exception("El modelo de inventario no es Intervalo Fijo. No se puede calcular la cantidad a pedir.");
        }
        articuloRepository.save(articulo);
        return cantAPedir;
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


    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
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
    public Long traerUnIdArticuloNombre(String nombre) throws Exception {
        try {
            Long idArticuloNombre = articuloRepository.traerUnIdArticuloNombre(nombre);
            return idArticuloNombre;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Override
    public Long traerIdProveedor(String nombre) throws Exception {
        try {
            Long idProveedor = articuloRepository.traerUnIdArticuloNombre(nombre);
            return idProveedor;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
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
