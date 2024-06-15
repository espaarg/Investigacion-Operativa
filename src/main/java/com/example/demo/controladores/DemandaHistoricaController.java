package com.example.demo.controladores;

import com.example.demo.entidades.DemandaHistorica;
import com.example.demo.servicios.DemandaHistoricaService;
import com.example.demo.servicios.DemandaHistoricaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

   /* @GetMapping("/all")
    public ResponseEntity<?> traerTodosArticulos(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerTodosArticulos());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }*/



}
