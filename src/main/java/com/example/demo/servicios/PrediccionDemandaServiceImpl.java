package com.example.demo.servicios;

import com.example.demo.dtos.PrediccionDemandaDTO;
import com.example.demo.dtos.RegresionLinealDTO;
import com.example.demo.entidades.PrediccionDemanda;
import com.example.demo.repositorios.BaseRepository;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public double predecirDemandaPMP(PrediccionDemandaDTO prediccionDemandaDTO) throws Exception {
        try {
            Long idArticulo = prediccionDemandaDTO.getIdArticulo();
            List<Double> coeficientes = prediccionDemandaDTO.getCoeficientesPonderacion();
            int mesAPredecir = prediccionDemandaDTO.getMesAPredecir();
            int anioAPredecir = prediccionDemandaDTO.getAnioAPredecir();
            int cantidadPeriodos= prediccionDemandaDTO.getCantidadPeriodosAtrasPMP();

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
            //System.out.println(suma);

            return (suma);

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public double predecirDemandaPMSuavizadoExponencial(PrediccionDemandaDTO prediccionPMSEDTO) throws Exception{
        try {
            Long idArticulo = prediccionPMSEDTO.getIdArticulo();
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
            //System.out.println(prediccion);
            //System.out.println(prediccionMesAnterior);
            //System.out.println(demanda);
            return prediccion;

        } catch (Exception e) {
            throw new Exception("Error al calcular la prediccionPMSE: " + e.getMessage());
        }
    }

    @Override
    public double predecirDemandaEstacional(PrediccionDemandaDTO prediccionEstacionalDTO) throws Exception {
        try {
            Long idArticulo = prediccionEstacionalDTO.getIdArticulo();
            int cantidadDePeriodos = prediccionEstacionalDTO.getCantidadDePeriodosEST();
            int cantidadDeAniosAtras = prediccionEstacionalDTO.getCantidadDeaniosAtrasEST();
            int cantUnidadesEsperadas = prediccionEstacionalDTO.getCantUnidadesEsperadasEST();
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

            System.out.println(demandaPredicha);
            return demandaPredicha;

        } catch (Exception e) {
            throw new Exception("Error al calcular la predicción de demanda estacional: " + e.getMessage());
        }
    }

    public Integer calcularRegresionLineal(RegresionLinealDTO regresionLinealDTO) throws Exception{
        //El método recibe un objeto RegresionLinealDTO que contiene información como el artículo,
        // el mes y año a predecir, y la cantidad de periodos a considerar.
        try {
            Long idArticulo = regresionLinealDTO.getArticulo().getId();
            int mesAPredecir = regresionLinealDTO.getMesAPredecir();

            //Se inicializan variables para almacenar las sumas de los valores necesarios
            // para calcular los coeficientes (sumaPeriodos, sumaDemandas, sumaXY, sumaX2, a, b).

            int sumaPeriodos = 0;
            int sumaDemandas = 0;
            int sumaXY = 0;
            double sumaX2 = 0;
            double a = 0.0;
            double b = 0.0;
            //for, se itera sobre el número de periodos especificado en cantidadPeriodos.
            // Para cada periodo, se calculan las fechas correspondientes (fechaDesde y fechaHasta)
            // y se obtiene la demanda histórica utilizando el servicio demandaHistoricaService.

            for(int i = 0; i < regresionLinealDTO.getCantidadPeriodos(); i++) {
                LocalDate fechaPrediccion = LocalDate.of(regresionLinealDTO.getAnioAPredecir(), regresionLinealDTO.getMesAPredecir(), 1);
                LocalDate fechaDesdeLocalDate = fechaPrediccion.minusMonths(i + 1).withDayOfMonth(1);
                LocalDate fechaHastaLocalDate = fechaPrediccion.minusMonths(i + 1).withDayOfMonth(fechaPrediccion.minusMonths(i + 1).lengthOfMonth());

                // Convertir LocalDate a String
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String fechaDesde = fechaDesdeLocalDate.format(formatter);
                String fechaHasta = fechaHastaLocalDate.format(formatter);

                int nroMes = fechaPrediccion.minusMonths(i + 1).getMonthValue(); // obtiene el número de mes (para los x)

                int demandaHistoricaMes = demandaHistoricaService.calcularDemandaHistorica(regresionLinealDTO.getArticulo().getId(), fechaDesde, fechaHasta);

                //Dentro del bucle, se actualizan las sumas sumaXY, sumaX2, sumaDemandas y sumaPeriodos
                // con los valores calculados para cada periodo.

                sumaXY += (nroMes * demandaHistoricaMes);
                sumaX2 += Math.pow(nroMes, 2);
                sumaDemandas += demandaHistoricaMes;
                sumaPeriodos += (nroMes);
            }
            if (regresionLinealDTO.getCantidadPeriodos() > 0) {
            //Coeficientes para la regresion Lineal
            //Después de obtener las sumas necesarias, se calculan los coeficientes a (intercepto) y b (pendiente) de la regresión lineal.
            // Esto se hace verificando si hay más de un periodo (cantidadPeriodos > 1). Si solo hay un periodo, se lanza una excepción indicando que no hay suficientes datos para calcular la regresión.
                int promedioPeriodos = sumaPeriodos / regresionLinealDTO.getCantidadPeriodos(); // esto seria Tp o x con barrita
                int promedioDemandas = sumaDemandas / regresionLinealDTO.getCantidadPeriodos(); // esto seria Yp o y con barrita
                double promPeriodosCuadrado = Math.pow(promedioPeriodos,2);
                if (regresionLinealDTO.getCantidadPeriodos() > 1) {
                    b = (sumaXY - (regresionLinealDTO.getCantidadPeriodos() * promedioPeriodos * promedioDemandas)) / (sumaX2 - (regresionLinealDTO.getCantidadPeriodos() * promPeriodosCuadrado));
                    a = promedioDemandas - (b * promedioPeriodos);
                } else {
                    // Si solo hay un periodo, la pendiente (b) no se puede calcular
                    throw new Exception("No hay suficientes periodos para calcular la regresión lineal.");
                }

            // Calcular la predicción de la demanda utilizando la regresión lineal
            //se utiliza la fórmula de regresión lineal
            // valorPrediccion = a + b * mesAPredecir
            // para calcular y devolver el valor predicho de la demanda.
            int valorPrediccion = (int) (a + (b * mesAPredecir));

            return valorPrediccion;
            } else {
                throw new Exception("No se encontraron datos de ventas históricas para el período especificado.");
            }
        } catch (Exception e) {
            throw new Exception("Error al calcular la regresión lineal: " + e.getMessage());
        }
    }
    @Override
    public void predecirDemandas(PrediccionDemandaDTO prediccionDemandaDTO) throws Exception {
        try{
            Long idArticulo= prediccionDemandaDTO.getIdArticulo();
            int anioAPredecir= prediccionDemandaDTO.getAnioAPredecir();
            int mesAPredecir=prediccionDemandaDTO.getMesAPredecir();
            double prediccionPMP= predecirDemandaPMP(prediccionDemandaDTO);
            double prediccionPMSE= predecirDemandaPMSuavizadoExponencial(prediccionDemandaDTO);
            double prediccionEST= predecirDemandaEstacional(prediccionDemandaDTO);

            //calculo del error
            LocalDate inicioPeriodo = LocalDate.of(anioAPredecir,mesAPredecir, 1);
            LocalDate finPeriodo = inicioPeriodo.withDayOfMonth(inicioPeriodo.lengthOfMonth());
            Date fechaDesdeDate = java.sql.Date.valueOf(inicioPeriodo);
            Date fechaHastaDate = java.sql.Date.valueOf(finPeriodo);

            int demandaReal = demandaHistoricaService.buscarDemandaAnual(idArticulo, fechaDesdeDate, fechaHastaDate);

            //error si no hay demanda para comparar
            if (demandaReal<=0){
                throw new Exception("No esta cargada la demanda necesaria para predecir ");
            }

            double errorPMP= prediccionPMP- demandaReal;
            double errorPMSE= prediccionPMSE- demandaReal;
            double errorEST= prediccionEST- demandaReal;

            if(errorPMP<errorPMSE){
                if(errorPMP<errorEST){
                    PrediccionDemanda prediccionDemanda = new PrediccionDemanda();
                    //le seteo errorPMP
                } else {
                    //le seteo error EST
                }
            } else {
                //le seteo error PMSE
            }


        }catch (Exception e){
            throw new Exception("Error al calcular la predicción de demanda: " + e.getMessage());
        }
    }


    @Override
    public Page findAllPageable(Pageable pageable) throws Exception {
        return null;
    }
}
