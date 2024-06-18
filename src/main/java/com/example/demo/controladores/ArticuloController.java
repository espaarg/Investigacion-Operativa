package com.example.demo.controladores;

import com.example.demo.entidades.Articulo;
import com.example.demo.servicios.ArticuloService;
import com.example.demo.servicios.ArticuloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins =  "*")
@RequestMapping(path = "/Articulo")
public class ArticuloController extends BaseControllerImpl<Articulo, ArticuloServiceImpl> {

    @Autowired
    private ArticuloService articuloService;

    @GetMapping("/all")
    public ResponseEntity<?> traerTodosArticulos(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerTodosArticulos());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

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
    @GetMapping("/articulosFaltantes")
    public ResponseEntity<?> identificarArticulosFaltantes(int stockDeSeguridad, int stockActual) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerArticulosFaltantes(stockDeSeguridad, stockActual));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @PutMapping("/baja/{id}")
    public ResponseEntity<String> darDeBajaArticulo(@PathVariable Long id) {
        try {
            Articulo articulo = articuloService.traerUnArticuloId(id);
            articuloService.darDeBajaArticulo(articulo);
            return ResponseEntity.ok("Artículo dado de baja correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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
   /* @GetMapping("/calcularCGI")
    public ResponseEntity<?> calcularCGI(int stockActual, float precioCompra) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.calcularCGI(stockActual, precioCompra));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }*/


    /*
    public ResponseEntity<?> search(String denominacion) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.search(denominacion));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }*/

}
