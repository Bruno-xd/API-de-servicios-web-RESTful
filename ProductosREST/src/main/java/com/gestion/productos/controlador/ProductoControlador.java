package com.gestion.productos.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.productos.modelo.Producto;
import com.gestion.productos.servicio.ProductoServicio;
//PRUEBA RAMA--------------
@RestController
public class ProductoControlador {

	@Autowired
	private ProductoServicio servicio;

//-----------------------------------------------LISTAR(GET)-----------------------------------------------------------
	@GetMapping("/productos")
	public List<Producto> listarProductos() {
		return servicio.listarProductos();
	}

//	@GetMapping("/productos/{id}")
//	public Producto obtenerProducto(@PathVariable Integer id) {
//		return servicio.obtenerProductoPorId(id);
//	}

//-----------------------------------------------BUSCAR------------------------------------------------------------
	// EL RESPONSEENTITY ES UNA CLASE CON UN CUERPO(BODY) Y CODIGO DE ESTADO
	// CUERPO->PRODUCTO / ESTADO-OK
	// EL @PathVariable es el {id}
	@GetMapping("/productos/{id}")
	public ResponseEntity<Producto> obtenerProducto(@PathVariable Integer id) {
		try {
			Producto producto = servicio.obtenerProductoPorId(id);
			return new ResponseEntity<Producto>(producto, HttpStatus.OK);
		} catch (Exception exception) {
			// CODIGO DE ESTADO->NOT_FOUND
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
		}
	}

//-----------------------------------------------REGISTRAR(POST)------------------------------------------------------------
	// EL @REQUESTBODY ES PARA ENVIARLE UN CUERPO
	@PostMapping("/productos")
	public void guardarProducto(@RequestBody Producto producto) {
		servicio.guardarProducto(producto);
	}

//-----------------------------------------------ACTUALIZAR(PUT)------------------------------------------------------------
	//POR EL @PATHVARIABLE OBTENEMOS UN PRODUCTO POR ID
	//GUARDAMOS EL PRODUCTO OBTENIDO EN UN PRODUCTO EXISTENTE
	@PutMapping("productos/{id}")
	public ResponseEntity<?> actualizarProducto(@RequestBody Producto producto, @PathVariable Integer id) {
		try {
			Producto ProductoActual = servicio.obtenerProductoPorId(id);
			
			ProductoActual.setNombre(producto.getNombre());
			ProductoActual.setPrecio(producto.getPrecio());
			
			servicio.guardarProducto(ProductoActual);
			return new ResponseEntity<Producto>(HttpStatus.OK); 
		} catch (Exception exception) {
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
		}
	}
	
//-----------------------------------------------ELIMINAR(DELETE)------------------------------------------------------------
	@DeleteMapping("productos/{id}")
	public void eliminarProducto(@PathVariable Integer id) {
		servicio.eliminarProducto(id);
	}

}
