package com.example.demo.servicios;

import com.example.demo.dtos.PrediccionPMPDTO;
import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.PrediccionDemanda;
import com.example.demo.repositorios.BaseRepository;
import lombok.Data;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class PrediccionDemandaServiceImpl extends BaseServiceImpl<PrediccionDemanda, Long> implements PrediccionDemandaService{
    public PrediccionDemandaServiceImpl(BaseRepository<PrediccionDemanda, Long> baseRepository) {
        super(baseRepository);
    }

    @Autowired
    private DemandaHistoricaServiceImpl demandaHistoricaService;

    public Integer predecirDemandaPMP(PrediccionPMPDTO prediccionPMPDTO) throws Exception {
        try {
            Articulo articulo = prediccionPMPDTO.getArticulo();
            Long idArticulo = articulo.getId();
            List<Double> coeficientes = prediccionPMPDTO.getCoeficientesPonderacion();
            int mesAPredecir = prediccionPMPDTO.getMesAPredecir();
            int anioAPredecir = prediccionPMPDTO.getAnioAPredecir();
            int cantidadPeriodos= prediccionPMPDTO.getCantidadPeriodosAtras();

            List<Integer> demandasHistoricas = new ArrayList<>();

            // Crear demanda histórica para los tres meses anteriores al mes seleccionado
            for (int i =cantidadPeriodos; i > 0; i--) {
                LocalDate inicioMes = LocalDate.of(anioAPredecir, mesAPredecir, 1).minus(i, ChronoUnit.MONTHS);
                LocalDate finMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

                String fechaDesde = inicioMes.toString();
                String fechaHasta = finMes.toString();

                demandaHistoricaService.crearDemandaHistorica(idArticulo, fechaDesde, fechaHasta);

                Date fechaDesdeDate = java.sql.Date.valueOf(inicioMes);
                Date fechaHastaDate = java.sql.Date.valueOf(finMes);

                // Obtener la demanda histórica creada
                int demanda = demandaHistoricaService.buscarDemandaAnual(idArticulo, fechaDesdeDate, fechaHastaDate);
                demandasHistoricas.add(demanda);
            }

            // Imprimir valores de coeficientes y demandas históricas para depuración
            //System.out.println("Coeficientes: " + coeficientes);
            //System.out.println("Demandas históricas: " + demandasHistoricas);

            if (coeficientes.size() != demandasHistoricas.size()) {
                throw new Exception("La cantidad de coeficientes de ponderación debe ser igual a la cantidad de periodos.");
            }

            // Calcular la predicción de la demanda utilizando el Promedio Móvil Ponderado
            double sumaPonderada = 0.0;
            double sumaPesos = 0.0;

            for (int i = 0; i < coeficientes.size(); i++) {
                sumaPonderada += demandasHistoricas.get(i) * coeficientes.get(i);
                sumaPesos += coeficientes.get(i);
            }

            // Imprimir valores de suma ponderada y suma de pesos para depuración
            //System.out.println("Suma ponderada: " + sumaPonderada);
            //System.out.println("Suma de pesos: " + sumaPesos);

            return (sumaPesos != 0) ? (int) (sumaPonderada / sumaPesos) : 0;

        } catch (Exception e) {
            throw new Exception("Error al calcular la prediccionPMP: " + e.getMessage());
        }
    }

    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }
}
