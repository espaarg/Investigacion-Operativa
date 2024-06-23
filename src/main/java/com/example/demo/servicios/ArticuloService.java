package com.example.demo.servicios;

import com.example.demo.dtos.ArticuloDTO;
import com.example.demo.entidades.Articulo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ArticuloService extends BaseService<Articulo, Long> {

    List<ArticuloDTO> traerTodosArticulos() throws Exception;
    List<Articulo> traerUnArticuloNombre(String nombre) throws Exception;
    Long traerUnIdArticuloNombre(String nombre) throws Exception;

    Long traerIdProveedor(String nombre) throws Exception;

    Articulo traerUnArticuloId(Long id) throws Exception;
    List<Articulo> traerArticuloBajoStock(int stockDeSeguridad, int stockActual) throws Exception;

    @Transactional(readOnly = true)
    List<Articulo> traerArticulosFaltantes(int stockDeSeguridad, int stockActual) throws Exception;

    void crearArticulo(String nombre, long precioCompra, int stockDeSeguridad, int stockActual, int loteOptimo, int cantMax, String modeloInventario, String proveedorArticulo, Float cgiArticulo, int cantAPedir, int puntoPedido, int tiempoEntrePedidos) throws Exception;

    void actualizarArticulo(Long id, String nombre, Float precioCompra, Integer stockDeSeguridad, Integer stockActual, Integer loteOptimo, Integer cantMax, String modeloInventario, String proveedorArticulo, Float cgiArticulo, Integer cantAPedir, Integer puntoPedido, Integer tiempoEntrePedidos) throws Exception;

    public double calcularLoteOptimo(Long idArticulo) throws Exception;

    float calcularCostoAlmacenamiento(Long idArticulo, Long idMultiplicador) throws Exception;

    int calcularPuntoPedido(Long idArticulo, Long idProveedor) throws Exception;

    public void darDeBajaArticulo(Articulo articulo) throws Exception;

    int calcularStockDeSeguridad(Long idArticulo) throws Exception;

    int calcularCantidadMaxima(Long idArticulo) throws Exception;

    int calcularCantidadAPedir(Long idArticulo) throws Exception;

    double calcularCGI(Long idArticulo) throws Exception;

    public void calculoLoteFijo(Long idArticulo, Long idProveedor, Long idMultiplicador) throws Exception;

    public void calculoIntervaloFijo(Long idArticulo) throws Exception;

    public void actualizarValores() throws Exception;

/*    List<ControlStockDTO> controlStockInsuficiente() throws Exception;

            List<ControlStockDTO> controlStockBajo() throws Exception;*/
/*
    Page<Articulo> search(String denominacion, Number min, Number max, Number stockMenor, Number minStock, Number maxStock, Pageable pageable) throws Exception;
*/


}

