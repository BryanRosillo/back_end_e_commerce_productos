package com.ecommerce.backend.productos.entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@NotBlank(message = "El nombre del producto no puede ser nulo.")
	private String nombre;
	
	@NotNull(message = "El precio del producto no puede ser nulo.")
	@Positive(message = "El precio del producto debe ser un valor positivo.")
	private double precio;

	@NotBlank(message = "La descripción del producto no puede ser nulo.")
	private String descripcion;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] imagen;
	
	private Long idUsuario;
	

}
