package com.ecommerce.backend.productos.servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.backend.productos.entidades.Producto;
import com.ecommerce.backend.productos.excepciones.ExcepcionProducto;
import com.ecommerce.backend.productos.repositorios.RepositorioProducto;


@Service
public class ServicioProducto {
	
	private final RepositorioProducto respositorioProducto;
	
	@Autowired
    private ServicioProducto(RepositorioProducto respositorioProducto) {
		this.respositorioProducto = respositorioProducto;
	}
	
	public Producto guardaProducto(Producto producto){
        return this.respositorioProducto.save(producto);
    }

    public Producto buscarProductoId(Long id) throws ExcepcionProducto{
        return respositorioProducto.findById(id)
                            .orElseThrow(() -> new ExcepcionProducto("Producto no encontrado"));
    }

    public Iterable<Producto> devolverProductos(){
        return this.respositorioProducto.findAll();
    }

    public Producto editarProducto(Long id, Producto productoEditado) {
        Producto productoExistente = respositorioProducto.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        productoExistente.setNombre(productoEditado.getNombre());
        productoExistente.setPrecio(productoEditado.getPrecio());
        productoExistente.setDescripcion(productoEditado.getDescripcion());

        if (productoEditado.getImagen() != null && productoEditado.getImagen().length > 0) {
            productoExistente.setImagen(productoEditado.getImagen());
        }

        return respositorioProducto.save(productoExistente);
    }



}
