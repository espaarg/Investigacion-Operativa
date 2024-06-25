package com.example.demo.controladores;

import com.example.demo.controladores.BaseControllerImpl;
import com.example.demo.dtos.CrearVentaDTO;
import com.example.demo.entidades.Venta;
import com.example.demo.servicios.VentaService;
import com.example.demo.servicios.VentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins =  "*")
@RequestMapping(path = "/Venta")
public class VentaController extends BaseControllerImpl<Venta, VentaServiceImpl> {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/all")
    public ResponseEntity<?> traerTodasVentas() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerTodasVentas());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/findVentasEntreFechas")
    public ResponseEntity<?> findVentasByFechas(@RequestParam String desde, @RequestParam String hasta) {
        try {
            String fechaDesde = desde;
            String fechaHasta = hasta;
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findVentasEntreFechas(fechaDesde, fechaHasta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> crearVenta(@RequestBody CrearVentaDTO ventaDTO) throws Exception {
        try {
            String string = ventaService.crearVenta(ventaDTO);
            return ResponseEntity.ok("Venta creada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }

    }

}