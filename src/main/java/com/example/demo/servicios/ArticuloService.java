package com.example.demo.servicios;

import com.example.demo.entidades.Articulo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ArticuloService extends BaseService<Articulo, Long> {

    List<Articulo> traerTodosArticulos() throws Exception;

    double calcularCGI() throws Exception;
    List<Articulo> traerUnArticuloNombre(String nombre) throws Exception;
    Articulo traerUnArticuloId(Long id) throws Exception;
    List<Articulo> traerArticuloBajoStock(int stockDeSeguridad, int stockActual) throws Exception;

    /*double calcularCGI(int stockActual, float precioCompra) throws Exception;
    List<Double> calcularCGIDeTodosArticulos() throws Exception;*/

    @Transactional(readOnly = true)
    List<Articulo> traerArticulosFaltantes(int stockDeSeguridad, int stockActual) throws Exception;

/*    List<ControlStockDTO> controlStockInsuficiente() throws Exception;

            List<ControlStockDTO> controlStockBajo() throws Exception;*/
/*
    Page<Articulo> search(String denominacion, Number min, Number max, Number stockMenor, Number minStock, Number maxStock, Pageable pageable) throws Exception;
*/

    public double calcularLoteOptimo(Long idArticulo) throws Exception;

    float calcularCostoAlmacenamiento(Long idArticulo, Long idMultiplicador) throws Exception;

    int calcularPuntoPedido(Long idArticulo, Long idProveedor) throws Exception;

    public void darDeBajaArticulo(Articulo articulo) throws Exception;




    /*public double calcularStockDeSeguridad() throws Exception;*/

}

