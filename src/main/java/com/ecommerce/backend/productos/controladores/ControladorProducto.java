package com.ecommerce.backend.productos.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ecommerce.backend.productos.entidades.Producto;
import com.ecommerce.backend.productos.excepciones.ExcepcionProducto;
import com.ecommerce.backend.productos.servicios.ServicioProducto;
import jakarta.validation.Valid;


@RestController
@RequestMapping(path="/productos", produces="application/json")
public class ControladorProducto {

	
	private final ServicioProducto servicioProducto;

    @Autowired
	public ControladorProducto(ServicioProducto servicioProducto) {
        this.servicioProducto = servicioProducto;
    }
    
    
    @PostMapping
    public ResponseEntity<Object> crearProducto(@RequestBody @Valid Producto producto, @RequestHeader("X-User-ID") Long idUsuario){
    	try {
    		producto.setIdUsuario(idUsuario);
    		this.servicioProducto.guardaProducto(producto);
    		return ResponseEntity.status(HttpStatus.CREATED).build();
    	}catch(Exception e) {
    		return ResponseEntity.badRequest().build();
    	}
    }
    
    @PatchMapping("/editar/{id}")
    public ResponseEntity<Object> editarProducto(@PathVariable Long id, @RequestBody Producto productoEditado) {
        try {
            Producto productoActualizado = servicioProducto.editarProducto(id, productoEditado);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarProductoId(@PathVariable Long id){
    	try {
    		return ResponseEntity.ok(this.servicioProducto.buscarProductoId(id));
    	}catch(ExcepcionProducto e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    }
	
    @GetMapping
    public ResponseEntity<Object> devolverProductos(){
        return ResponseEntity.ok(this.servicioProducto.devolverProductos());
    }


    @PostMapping("/upload/{id}")
    public ResponseEntity<String> subirImagenProducto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Producto producto = this.servicioProducto.buscarProductoId(id);
            producto.setImagen(file.getBytes());
            this.servicioProducto.guardaProducto(producto);
            return ResponseEntity.ok("Imagen subida exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al subir la imagen");
        }
    }
	
    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> devolverImagenProducto(@PathVariable Long id) {
        try{
            Producto producto = this.servicioProducto.buscarProductoId(id);
            return ResponseEntity.ok(producto.getImagen());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
