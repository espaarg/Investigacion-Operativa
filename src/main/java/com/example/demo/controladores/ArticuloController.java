package com.example.demo.controladores;

import com.example.demo.entidades.Articulo;
import com.example.demo.servicios.ArticuloService;
import com.example.demo.servicios.ArticuloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins =  "*")
@RequestMapping(path = "/Articulo")
public class ArticuloController extends BaseControllerImpl<Articulo, ArticuloServiceImpl> {

    @Autowired
    private ArticuloService articuloService;

    @GetMapping("/all")
    public ResponseEntity<?> traerTodosArticulos(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerTodosArticulos());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/oneNombre")
    public ResponseEntity<?> traerUnArticuloNombre(String nombre){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerUnArticuloNombre(nombre));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
    @GetMapping("/oneId")
    public ResponseEntity<?> traerUnArticuloId(Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerUnArticuloId(id));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
    @GetMapping("/allArticuloBajoStock")
    public ResponseEntity<?> traerArticuloBajoStock(int stockDeSeguridad, int stockActual){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerArticuloBajoStock(stockDeSeguridad, stockActual));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }
    @GetMapping("/articulosFaltantes")
    public ResponseEntity<?> identificarArticulosFaltantes(int stockDeSeguridad, int stockActual) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.traerArticulosFaltantes(stockDeSeguridad, stockActual));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @PutMapping("/baja/{id}")
    public ResponseEntity<String> darDeBajaArticulo(@PathVariable Long id) {
        try {
            Articulo articulo = articuloService.traerUnArticuloId(id);
            articuloService.darDeBajaArticulo(articulo);
            return ResponseEntity.ok("Artículo dado de baja correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/calcularLoteOptimo")
    public ResponseEntity<?> calcularLoteOptimo(@RequestParam Long idArticulo) {
        try {
            double loteOptimo = servicio.calcularLoteOptimo(idArticulo);
            return ResponseEntity.status(HttpStatus.OK).body("{\"loteOptimo\": " + loteOptimo + "}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/calcularCostoAlmacenamiento")
    public ResponseEntity<?> calcularCostoAlmacenamiento(@RequestParam Long idArticulo, @RequestParam Long idMultiplicador) {
        try {
            float costoAlmacenamiento = servicio.calcularCostoAlmacenamiento(idArticulo, idMultiplicador);
            return ResponseEntity.status(HttpStatus.OK).body("{\"costoAlmacenamiento\": " + costoAlmacenamiento + "}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/calcularPuntoPedido")
    public ResponseEntity<?> calcularPuntoPedido(@RequestParam Long idArticulo, @RequestParam Long idProveedor) {
        try {
            int puntoPedido = servicio.calcularPuntoPedido(idArticulo, idProveedor);
            return ResponseEntity.status(HttpStatus.OK).body("{\"puntoPedido\": " + puntoPedido + "}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/calcularStockDeSeguridad")
    public ResponseEntity<?> calcularStockDeSeguridad(@RequestParam Long idArticulo) {
        try {
            int stockDeSeguridad = servicio.calcularStockDeSeguridad(idArticulo);
            return ResponseEntity.status(HttpStatus.OK).body("{\"mensaje\": \"Stock de seguridad calculado correctamente\": "+ stockDeSeguridad + "}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/calcularCantidadMaxima")
    public ResponseEntity<?> calcularCantidadMaxima(@RequestParam Long idArticulo) {
        try {
            int cantMax = servicio.calcularCantidadMaxima(idArticulo);
            return ResponseEntity.status(HttpStatus.OK).body("{\"mensaje\": \"Cantidad máxima calculada correctamente\"}: "+ cantMax+"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/calcularCantidadAPedir")
    public ResponseEntity<?> calcularCantidadAPedir(@RequestParam Long idArticulo) {
        try {
            int cantAPedir = servicio.calcularCantidadAPedir(idArticulo);
            return ResponseEntity.status(HttpStatus.OK).body("{\"mensaje\": \"Cantidad a pedir calculada correctamente\"}: "+ cantAPedir+"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/calcularCGI")
    public ResponseEntity<?> calcularCGI(@RequestParam Long idArticulo) {
        try {
            float cgi = servicio.calcularCGI(idArticulo);
            return ResponseEntity.status(HttpStatus.OK).body("{\"mensaje\": \"CGI calculado correctamente\"}: "+ cgi+"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/calculoLoteFijo")
    public ResponseEntity<?> calculoLoteFijo(@RequestParam Long idArticulo, @RequestParam Long idProveedor, @RequestParam Long idMultiplicador) {
        try {
            double loteOptimo = articuloService.calcularLoteOptimo(idArticulo);
            float costoAlmacenamiento = articuloService.calcularCostoAlmacenamiento(idArticulo, idMultiplicador);
            int puntoPedido = articuloService.calcularPuntoPedido(idArticulo, idProveedor);
            int stockDeSeguridad = articuloService.calcularStockDeSeguridad(idArticulo);
            float cgi = articuloService.calcularCGI(idArticulo);

            return ResponseEntity.status(HttpStatus.OK).body(String.format(
                    "{\"loteOptimo\": %f, \"costoAlmacenamiento\": %f, \"puntoPedido\": %d, \"stockDeSeguridad\": %d, \"cgi\": %f}",
                    loteOptimo, costoAlmacenamiento, puntoPedido, stockDeSeguridad, cgi));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/calculoIntervaloFijo")
    public ResponseEntity<?> calculoIntervaloFijo(@RequestParam Long idArticulo) {
        try {
            int cantidadMaxima = articuloService.calcularCantidadMaxima(idArticulo);
            int cantidadAPedir = articuloService.calcularCantidadAPedir(idArticulo);
            int stockDeSeguridad = articuloService.calcularStockDeSeguridad(idArticulo);

            return ResponseEntity.status(HttpStatus.OK).body(String.format(
                    "{\"cantidadMaxima\": %d, \"cantidadAPedir\": %d, \"stockDeSeguridad\": %d}",
                    cantidadMaxima, cantidadAPedir, stockDeSeguridad));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> crearArticulo(@RequestParam String nombre, @RequestParam Long precioCompra, @RequestParam int stockActual, int stockDeSeguridad, int loteOptimo, int cantMax, @RequestParam String modeloInventario, @RequestParam String proveedorArticulo, Float cgiArticulo, int cantAPedir, int puntoPedido, int tiempoEntrePedidos){
        try{
            servicio.crearArticulo(nombre,precioCompra,stockDeSeguridad,stockActual,loteOptimo,cantMax,modeloInventario,proveedorArticulo,cgiArticulo,cantAPedir, puntoPedido, tiempoEntrePedidos);
            return ResponseEntity.status(HttpStatus.OK).body("Listo");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarArticulo(@RequestParam Long id,
                                                @RequestParam(required = false) String nombre,
                                                @RequestParam(required = false) Float precioCompra,
                                                @RequestParam(required = false) Integer stockActual,
                                                @RequestParam(required = false) Integer stockDeSeguridad,
                                                @RequestParam(required = false) Integer loteOptimo,
                                                @RequestParam(required = false) Integer cantMax,
                                                @RequestParam(required = false) String modeloInventario,
                                                @RequestParam(required = false) String proveedorArticulo,
                                                @RequestParam(required = false) Float cgiArticulo,
                                                @RequestParam(required = false) Integer cantAPedir,
                                                @RequestParam(required = false) Integer puntoPedido,
                                                @RequestParam(required = false) Integer tiempoEntrePedidos){
        try{
            servicio.actualizarArticulo(id,nombre,precioCompra,stockDeSeguridad,stockActual,loteOptimo,cantMax,modeloInventario,proveedorArticulo,cgiArticulo,cantAPedir, puntoPedido, tiempoEntrePedidos);
            return ResponseEntity.status(HttpStatus.OK).body("Listo");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

   /* @GetMapping("/calcularCGI")
    public ResponseEntity<?> calcularCGI(int stockActual, float precioCompra) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.calcularCGI(stockActual, precioCompra));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }*/


    /*
    public ResponseEntity<?> search(String denominacion) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.search(denominacion));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }*/

}
