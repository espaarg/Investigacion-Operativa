package com.example.demo.parametros.multiplicadorCostoAlmacenamiento;

import com.example.demo.controladores.BaseControllerImpl;
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
@RequestMapping(path = "/MCA")
public class MultiplicadorCostoAlmacenamientoController extends BaseControllerImpl<MultiplicadorCostoAlmacenamiento, MultiplicadorCostoAlmacenamientoServiceImpl> {

    @GetMapping("/all")
    public ResponseEntity<?> traerMCA(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerMCA());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

}
