package com.example.demo.controladores;

import com.example.demo.entidades.Articulo;
import com.example.demo.servicios.ArticuloServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> traerArticuloBajoStock(int stockDeSeguridad){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerArticuloBajoStock(stockDeSeguridad));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
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
