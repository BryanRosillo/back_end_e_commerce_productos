package com.ecommerce.backend.productos.repositorios;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.ecommerce.backend.productos.entidades.Producto;

public interface RepositorioProducto extends CrudRepository<Producto, Long> {
	
	Producto save(Producto producto);
	Optional<Producto> findById(Long id);
	Iterable<Producto> findAll();

}
