package com.example.demo.servicios;

import com.example.demo.entidades.Articulo;
import com.example.demo.repositorios.ArticuloRepository;
import com.example.demo.repositorios.BaseRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service

public class ArticuloServiceImpl extends BaseServiceImpl<Articulo, Long> implements ArticuloService{

    @Autowired
    private ArticuloRepository articuloRepository;

    public ArticuloServiceImpl(BaseRepository<Articulo, Long> baseRepository, ArticuloRepository articuloInsumoRepository) {
        super(baseRepository);
        this.articuloRepository = articuloRepository;
    }

/*    @Override
    public List<ControlStockDTO> controlStockInsuficiente() throws Exception {
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
    }


    @Override
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
    public List<Articulo> search(String nombre) throws Exception {
        try {
            List<Articulo> articuloInsumos = articuloRepository.searchNativo(nombre);
            return articuloInsumos;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



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