package com.example.demo.servicios;

import com.example.demo.dtos.PrediccionEstacionalDTO;
import com.example.demo.dtos.PrediccionPMPDTO;
import com.example.demo.dtos.PrediccionPMSEDTO;
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
import java.util.Date;
import java.util.List;

@Service

public class PrediccionDemandaServiceImpl extends BaseServiceImpl<PrediccionDemanda, Long> implements PrediccionDemandaService{
    public PrediccionDemandaServiceImpl(BaseRepository<PrediccionDemanda, Long> baseRepository) {
        super(baseRepository);
    }

    @Autowired
    private DemandaHistoricaServiceImpl demandaHistoricaService;

    public double predecirDemandaPMP(PrediccionPMPDTO prediccionPMPDTO) throws Exception {
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

                Date fechaDesdeDate = java.sql.Date.valueOf(inicioMes);
                Date fechaHastaDate = java.sql.Date.valueOf(finMes);

                // Obtener la demanda histórica creada
                int demanda = demandaHistoricaService.buscarDemandaAnual(idArticulo, fechaDesdeDate, fechaHastaDate);

                if(demanda == 0) {
                    demandaHistoricaService.crearDemandaHistorica(idArticulo, fechaDesde, fechaHasta);
                    int demanda1 = demandaHistoricaService.buscarDemandaAnual(idArticulo, fechaDesdeDate, fechaHastaDate);
                    demandasHistoricas.add(demanda1);
                } else{
                    demandasHistoricas.add(demanda);
                }

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

            return (sumaPesos != 0) ? (double) (sumaPonderada / sumaPesos) : 0;

        } catch (Exception e) {
            throw new Exception("Error al calcular la prediccionPMP: " + e.getMessage());
        }
    }

    public double calcularPromedioMovilMesAnterior(Long idArticulo, LocalDate fechaDesdeLocalDate) throws Exception{
        try{
            List<Integer> demandasHistoricas = new ArrayList<>();

            // Crear demanda histórica para los tres meses anteriores al mes seleccionado
            for (int i =3; i > 0; i--) {
                LocalDate inicioMes = fechaDesdeLocalDate.minus(i, ChronoUnit.MONTHS);
                LocalDate finMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

                String fechaDesde = inicioMes.toString();
                String fechaHasta = finMes.toString();

                Date fechaDesdeDate = java.sql.Date.valueOf(inicioMes);
                Date fechaHastaDate = java.sql.Date.valueOf(finMes);

                // Obtener la demanda histórica creada
                int demanda = demandaHistoricaService.buscarDemandaAnual(idArticulo, fechaDesdeDate, fechaHastaDate);

                if(demanda ==0) {
                    demandaHistoricaService.crearDemandaHistorica(idArticulo, fechaDesde, fechaHasta);
                    int demanda1 = demandaHistoricaService.buscarDemandaAnual(idArticulo, fechaDesdeDate, fechaHastaDate);
                    demandasHistoricas.add(demanda1);
                } else{
                    demandasHistoricas.add(demanda);
                }


            }
            // Calcular la predicción de la demanda utilizando el Promedio Móvil
            double suma= 0.0;



            for (int j = 0; j < 3; j++) {
                suma += demandasHistoricas.get(j);
            }

            suma= suma/3;
            System.out.println(suma);

            return (suma);

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public double predecirDemandaPMSuavizadoExponencial(PrediccionPMSEDTO prediccionPMSEDTO) throws Exception{
        try {
            Articulo articulo = prediccionPMSEDTO.getArticulo();
            Long idArticulo = articulo.getId();
            int mesAPredecir = prediccionPMSEDTO.getMesAPredecir();
            int anioAPredecir = prediccionPMSEDTO.getAnioAPredecir();
            double alfa= prediccionPMSEDTO.getAlfa();

            // Crear demanda histórica para el mes anterior al mes seleccionado
            LocalDate inicioMes = LocalDate.of(anioAPredecir, mesAPredecir, 1).minus(1, ChronoUnit.MONTHS);
            LocalDate finMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

            String fechaDesde = inicioMes.toString();
            String fechaHasta = finMes.toString();
            Date fechaDesdeDate = java.sql.Date.valueOf(inicioMes);
            Date fechaHastaDate = java.sql.Date.valueOf(finMes);

            // Obtener la demanda histórica creada
            int demanda = demandaHistoricaService.buscarDemandaAnual(idArticulo, fechaDesdeDate, fechaHastaDate);

            if(demanda ==0) {
                demandaHistoricaService.crearDemandaHistorica(idArticulo, fechaDesde, fechaHasta);
                int demanda1 = demandaHistoricaService.buscarDemandaAnual(idArticulo, fechaDesdeDate, fechaHastaDate);
                demanda= demanda1;
            }

            //obtengo prediccion mes anterior con promedio movil
            LocalDate fechaDesdeLocalDate = java.sql.Date.valueOf(inicioMes).toLocalDate();
            double prediccionMesAnterior;

            prediccionMesAnterior= calcularPromedioMovilMesAnterior(idArticulo, fechaDesdeLocalDate);

            //calculo el pronostico
            double prediccion=0.0;
            prediccion= prediccionMesAnterior + alfa * (demanda - prediccionMesAnterior);
            System.out.println(prediccion);
            System.out.println(demanda);
            return prediccion;

        } catch (Exception e) {
            throw new Exception("Error al calcular la prediccionPMSE: " + e.getMessage());
        }
    }

    @Override
    public double predecirDemandaEstacional(PrediccionEstacionalDTO prediccionEstacionalDTO) throws Exception {
        try {
            Articulo articulo = prediccionEstacionalDTO.getArticulo();
            Long idArticulo = articulo.getId();
            int cantidadDePeriodos = prediccionEstacionalDTO.getCantidadDePeriodos();
            int cantidadDeAniosAtras = prediccionEstacionalDTO.getCantidadDeaniosAtras();
            int cantUnidadesEsperadas = prediccionEstacionalDTO.getCantUnidadesEsperadas();
            int mesAPredecir = prediccionEstacionalDTO.getMesAPredecir();
            int anioAPredecir = prediccionEstacionalDTO.getAnioAPredecir();

            double[] promediosAnuales = new double[cantidadDePeriodos];
            double promedioGeneral = 0.0;
            int[] demandasTotales = new int[cantidadDePeriodos];

            // Recopilar las demandas históricas y calcular el promedio de cada periodo para cada año
            for (int anio = 0; anio < cantidadDeAniosAtras; anio++) {
                for (int periodo = 0; periodo < cantidadDePeriodos; periodo++) {
                    LocalDate inicioPeriodo = LocalDate.of(anioAPredecir - anio - 1, periodo + 1, 1);
                    LocalDate finPeriodo = inicioPeriodo.withDayOfMonth(inicioPeriodo.lengthOfMonth());

                    String fechaDesde = inicioPeriodo.toString();
                    String fechaHasta = finPeriodo.toString();
                    Date fechaDesdeDate = java.sql.Date.valueOf(inicioPeriodo);
                    Date fechaHastaDate = java.sql.Date.valueOf(finPeriodo);

                    int demanda = demandaHistoricaService.buscarDemandaAnual(idArticulo, fechaDesdeDate, fechaHastaDate);
                    if (demanda <= 0) { // Verifica si la demanda es nula o no existe
                        // Llama al método para crear la demanda histórica
                        demandaHistoricaService.crearDemandaHistorica(idArticulo, fechaDesde, fechaHasta);
                        // Vuelve a consultar la demanda después de crearla
                        demanda = demandaHistoricaService.buscarDemandaAnual(idArticulo, fechaDesdeDate, fechaHastaDate);
                    }
                    demandasTotales[periodo] += demanda;
                }
            }

            // Calcular el promedio de cada periodo y el promedio general
            for (int periodo = 0; periodo < cantidadDePeriodos; periodo++) {
                promediosAnuales[periodo] = (double) demandasTotales[periodo] / cantidadDeAniosAtras;
                promedioGeneral += promediosAnuales[periodo];
            }
            promedioGeneral /= cantidadDePeriodos;

            // Calcular el índice de estacionalidad para cada periodo
            double[] indicesEstacionalidad = new double[cantidadDePeriodos];
            for (int periodo = 0; periodo < cantidadDePeriodos; periodo++) {
                indicesEstacionalidad[periodo] = promediosAnuales[periodo] / promedioGeneral;
            }

            // Calcular la predicción de la demanda
            double indiceEstacionalidadMesPredecir = indicesEstacionalidad[mesAPredecir-1];
            double demandaPredicha = ((double) cantUnidadesEsperadas / cantidadDePeriodos) * indiceEstacionalidadMesPredecir;

            return demandaPredicha;

        } catch (Exception e) {
            throw new Exception("Error al calcular la predicción de demanda estacional: " + e.getMessage());
        }
    }



    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }
}
