package com.example.demo.controladores;

import com.example.demo.controladores.BaseControllerImpl;
import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.VentaArticulo;
import com.example.demo.servicios.ArticuloServiceImpl;
import com.example.demo.servicios.VentaArticuloServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins =  "*")
@RequestMapping(path = "/VentaArticulo")
public class VentaArticuloController extends BaseControllerImpl<VentaArticulo, VentaArticuloServiceImpl> {

    @GetMapping("/id")
    public ResponseEntity<?> todoDetalleVenta(Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.todoDetalleVenta(id));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

}