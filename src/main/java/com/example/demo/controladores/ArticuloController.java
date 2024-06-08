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

    /*@GetMapping("/search")
    public ResponseEntity<?> search(String denominacion) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.search(denominacion));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }*/

}
