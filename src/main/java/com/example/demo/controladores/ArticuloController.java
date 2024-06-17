package com.example.demo.controladores;

import com.example.demo.entidades.Articulo;
import com.example.demo.servicios.ArticuloServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins =  "*")
@RequestMapping(path = "/Articulo")
public class ArticuloController extends BaseControllerImpl<Articulo, ArticuloServiceImpl> {

    @GetMapping("/all")
    public ResponseEntity<?> traerTodosArticulos(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerTodosArticulos());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

 /*   @PostMapping("/create")
    public ResponseEntity<?> crearArticulo(@RequestParam String nombre,
                                                   @RequestParam float precioCompra,
                                                   @RequestParam int stockActual,
                                           @RequestParam int loteOptimo,
                                           @RequestParam float cgiArticulo,
                                           @RequestParam int puntoPedido,
                                           @RequestParam int stockDeSeguridad
                                           ) {
        try {
            demandaHistoricaService.crearDemandaHistorica(idArticulo, fechaDesde, fechaHasta);
            return ResponseEntity.status(HttpStatus.CREATED).body("DemandaHistorica creada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }*/

    @GetMapping("/oneNombre")
    public ResponseEntity<?> traerUnArticuloNombre(String nombre){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerUnArticuloNombre(nombre));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
    @GetMapping("/oneId")
    public ResponseEntity<?> traerUnArticuloId(Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerUnArticuloId(id));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
    @GetMapping("/allArticuloBajoStock")
    public ResponseEntity<?> traerArticuloBajoStock(int stockDeSeguridad, int stockActual){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerArticuloBajoStock(stockDeSeguridad, stockActual));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/calcularLoteOptimo")
    public ResponseEntity<?> calcularLoteOptimo(@RequestParam Long idArticulo) {
        try {
            double loteOptimo = servicio.calcularLoteOptimo(idArticulo);
            return ResponseEntity.status(HttpStatus.OK).body("{\"loteOptimo\": " + loteOptimo + "}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }


    /*
    public ResponseEntity<?> search(String denominacion) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.search(denominacion));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }*/

}
