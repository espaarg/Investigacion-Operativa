package com.example.demo.controladores;

import com.example.demo.dtos.PrediccionEstacionalDTO;
import com.example.demo.dtos.PrediccionPMPDTO;
import com.example.demo.dtos.PrediccionPMSEDTO;
import com.example.demo.dtos.RegresionLinealDTO;
import com.example.demo.entidades.PrediccionDemanda;
import com.example.demo.servicios.PrediccionDemandaService;
import com.example.demo.servicios.PrediccionDemandaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/PrediccionDemanda")

public class PrediccionDemandaController extends BaseControllerImpl<PrediccionDemanda, PrediccionDemandaServiceImpl> {

    @Autowired
    private PrediccionDemandaService prediccionDemandaService;

    @PostMapping("/predecirPMP")
    public ResponseEntity<?> predecirDemandaPMP(@RequestBody PrediccionPMPDTO prediccionPMPDTO) {
        try {
            double resultado = prediccionDemandaService.predecirDemandaPMP(prediccionPMPDTO);
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/predecirPMSE")
    public ResponseEntity<?> predecirDemandaPMSE(@RequestBody PrediccionPMSEDTO prediccionPMSEDTO) {
        try {
            double resultado = prediccionDemandaService.predecirDemandaPMSuavizadoExponencial(prediccionPMSEDTO);
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

  @PostMapping("/predecirEstacional")
    public ResponseEntity<?> predecirDemandaEstacional(@RequestBody PrediccionEstacionalDTO prediccionEstacionalDTO) {
        try {
            System.out.println("Recibido: " + prediccionEstacionalDTO);
            double resultado = prediccionDemandaService.predecirDemandaEstacional(prediccionEstacionalDTO);
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/regresionLineal")
    public ResponseEntity<?> calcularRegresionLineal(@RequestBody RegresionLinealDTO regresionLinealDTO) {
        try {
            Integer resultado = prediccionDemandaService.calcularRegresionLineal(regresionLinealDTO);
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
