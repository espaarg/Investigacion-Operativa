package com.example.demo.controladores;

import com.example.demo.entidades.DemandaHistorica;
import com.example.demo.servicios.DemandaHistoricaService;
import com.example.demo.servicios.DemandaHistoricaServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@RestController
@CrossOrigin(origins =  "*")
@RequestMapping(path = "/DemandaHistorica")
public class   DemandaHistoricaController {
    @Autowired
    private DemandaHistoricaService demandaHistoricaService;

    @GetMapping("/all")
    public ResponseEntity<?> traerTodasDemandasH(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(demandaHistoricaService.traerTodasDemandasH());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> crearDemandaHistorica(@RequestParam Long idArticulo,
                                                   @RequestParam String fechaDesde,
                                                   @RequestParam String fechaHasta) {
        try {
            demandaHistoricaService.crearDemandaHistorica(idArticulo, fechaDesde, fechaHasta);
            return ResponseEntity.status(HttpStatus.CREATED).body("DemandaHistorica creada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/create2")
    public ResponseEntity<?> crearDemandaH(@RequestParam Long idArticulo,
                                                   @RequestParam String fechaDesde,
                                                   @RequestParam String fechaHasta) {
        try {
            demandaHistoricaService.crearDemandaHistorica(idArticulo, fechaDesde, fechaHasta);
            return ResponseEntity.status(HttpStatus.CREATED).body("DemandaHistorica creada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDemandaHistorica(@PathVariable Long id) {
        try {
            demandaHistoricaService.eliminarDemandaHistorica(id);
            return ResponseEntity.status(HttpStatus.OK).body("DemandaHistorica eliminada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/calcularDemandaHistorica")
    public ResponseEntity<?> calcularDemandaHistorica(@RequestParam String desde, @RequestParam String hasta, @RequestParam Long idArticulo) {
        try {
            int demanda = demandaHistoricaService.calcularDemandaHistorica(idArticulo, desde, hasta);
            if (demanda == -1) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artículo no encontrado en el rango de fechas proporcionado");
            }
            return ResponseEntity.ok(demanda);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado: " + e.getMessage());
        }
    }



}
