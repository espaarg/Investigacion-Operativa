package com.example.demo.controladores;

import com.example.demo.controladores.BaseControllerImpl;
import com.example.demo.dtos.PedidoArticuloDTO;
import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.OrdenDeCompra;
import com.example.demo.servicios.ArticuloServiceImpl;
import com.example.demo.servicios.OrdenDeCompraServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins =  "*")
@RequestMapping(path = "/OrdenDeCompra")
public class OrdenDeCompraController extends BaseControllerImpl<OrdenDeCompra, OrdenDeCompraServiceImpl> {

    @GetMapping("/all")
    public ResponseEntity<?> traerTodasOrdenes(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerTodasOrdenes());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    /*@GetMapping("/search")
    public ResponseEntity<?> search(String denominacion) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.search(denominacion));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }*/

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarOrden(@RequestParam Long id){
        try{
            servicio.confirmarOrden(id);
            return ResponseEntity.status(HttpStatus.OK).body("OK");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> crearOrdenDeCompra(
            @RequestParam Long idArticulo,
            @RequestParam int cantidad,
            @RequestParam Long idProveedor) {
        try {
            if (idArticulo == null || idProveedor == null || cantidad <= 0) {
                throw new IllegalArgumentException("Valores inválidos proporcionados");
            } else {
                servicio.crearOrdenDeCompra(idArticulo, cantidad, idProveedor);
                return ResponseEntity.status(HttpStatus.CREATED).body("Orden creada");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/findOrdenesByEstado")
    public ResponseEntity<?> findOrdenesByEstado(@RequestParam String estado) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.filtrarOrdenDeCompraPorEstado(estado));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

}
