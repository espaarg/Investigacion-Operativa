package com.example.demo.controladores;

import com.example.demo.entidades.Articulo;
import com.example.demo.entidades.PedidoArticulo;
import com.example.demo.servicios.ArticuloServiceImpl;
import com.example.demo.entidades.OrdenDeCompra;
import com.example.demo.servicios.PedidoArticuloServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/PedidoArticulo")
public class PedidoArticuloController extends BaseControllerImpl<PedidoArticulo, PedidoArticuloServiceImpl>{

    @GetMapping("/search")
    public ResponseEntity<?>search(@RequestParam String filtro) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.search(filtro));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> crearPedidoArticulo(@RequestParam int cantidad, long idArticulo, long idOrdenDecompra){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.crearPedidoArticulo(cantidad,idArticulo,idOrdenDecompra));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/searchPaged")
        public ResponseEntity<?> search(@RequestParam String filtro, Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.search(filtro));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }

    }
}

