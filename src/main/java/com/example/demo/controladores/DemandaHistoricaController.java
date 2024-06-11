package com.example.demo.controladores;

import com.example.demo.entidades.DemandaHistorica;
import com.example.demo.servicios.DemandaHistoricaService;
import com.example.demo.servicios.DemandaHistoricaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins =  "*")
@RequestMapping(path = "api/v1/demandaHistorica")
public class DemandaHistoricaController extends BaseControllerImpl<DemandaHistorica, DemandaHistoricaServiceImpl>{

    @Autowired
    private DemandaHistoricaService demandaHistoricaService;

    @PostMapping("/create")
    public ResponseEntity<?> crearDemandaHistorica(@RequestParam Long idArticulo, @RequestParam Date fechaDesde, @RequestParam Date fechaHasta) {
        try {
            demandaHistoricaService.crearDemandaHistorica(idArticulo, fechaDesde, fechaHasta);
            return ResponseEntity.status(HttpStatus.CREATED).body("DemandaHistorica creada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }



}
