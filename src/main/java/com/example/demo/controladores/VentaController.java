package com.example.demo.controladores;

import com.example.demo.controladores.BaseControllerImpl;
import com.example.demo.entidades.Venta;
import com.example.demo.servicios.VentaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins =  "*")
@RequestMapping(path = "api/v1/Venta")
public class VentaController extends BaseControllerImpl<Venta, VentaServiceImpl> {

    /*@GetMapping("/search")
    public ResponseEntity<?> search(String denominacion) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.search(denominacion));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }*/

    @GetMapping("/findVentasEntreFechas")
    public ResponseEntity<?> findVentasByFechas(@RequestParam Date desde, @RequestParam Date hasta) {
        try {
            Date fechaDesde = desde;
            Date fechaHasta = hasta;
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findVentasEntreFechas(fechaDesde, fechaHasta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
}