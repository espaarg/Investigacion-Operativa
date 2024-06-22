package com.example.demo.servicios;

import com.example.demo.dtos.PrediccionPMPDTO;
import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.PrediccionDemanda;
import com.example.demo.repositorios.BaseRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

            List<Integer> demandasHistoricas = new ArrayList<>();

            // Crear demanda histórica para los tres meses anteriores al mes seleccionado
            for (int i = 3; i > 0; i--) {
                LocalDate inicioMes = LocalDate.of(anioAPredecir, mesAPredecir, 1).minus(i, ChronoUnit.MONTHS);
                LocalDate finMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

                String fechaDesde = inicioMes.toString();
                String fechaHasta = finMes.toString();

                demandaHistoricaService.crearDemandaHistorica(idArticulo, fechaDesde, fechaHasta);

                // Obtener la demanda histórica creada
                int demanda = demandaHistoricaService.obtenerDemandaAnual(idArticulo);
                demandasHistoricas.add(demanda);
            }

            if (coeficientes.size() != demandasHistoricas.size()) {
                throw new Exception("La cantidad de coeficientes de ponderación debe ser igual a tres.");
            }

            // Calcular la predicción de la demanda utilizando el Promedio Móvil Ponderado
            double sumaPonderada = 0.0;
            double sumaPesos = 0.0;

            for (int i = 0; i < coeficientes.size(); i++) {
                sumaPonderada += demandasHistoricas.get(i) * coeficientes.get(i);
                sumaPesos += coeficientes.get(i);
            }

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
