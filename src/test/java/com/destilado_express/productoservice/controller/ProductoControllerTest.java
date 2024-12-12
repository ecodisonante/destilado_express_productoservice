package com.destilado_express.productoservice.controller;

import com.destilado_express.productoservice.model.Producto;
import com.destilado_express.productoservice.service.auth.AuthService;
import com.destilado_express.productoservice.service.product.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodosLosProductos_Admin() {
        List<Producto> productos = List.of(new Producto());
        when(authService.esAdmin()).thenReturn(true);
        when(productoService.getAllProductos()).thenReturn(productos);

        ResponseEntity<List<Producto>> response = productoController.obtenerTodosLosProductos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productos, response.getBody());
    }

    @Test
    void obtenerTodosLosProductos_User() {
        List<Producto> productos = List.of(new Producto());
        when(authService.esAdmin()).thenReturn(false);
        when(productoService.getProductosDisponibles()).thenReturn(productos);

        ResponseEntity<List<Producto>> response = productoController.obtenerTodosLosProductos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productos, response.getBody());
    }

    @Test
    void obtenerProductoPorId_Admin() {
        Producto producto = new Producto();
        producto.setDisponible(true);
        when(authService.esAdmin()).thenReturn(true);
        when(productoService.getProductoById(anyLong())).thenReturn(producto);

        ResponseEntity<Producto> response = productoController.obtenerProductoPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(producto, response.getBody());
    }

    @Test
    void obtenerProductoPorId_NotAvailable_NotAdmin() {
        Producto producto = new Producto();
        producto.setDisponible(false);
        when(authService.esAdmin()).thenReturn(false);
        when(productoService.getProductoById(anyLong())).thenReturn(producto);

        ResponseEntity<Producto> response = productoController.obtenerProductoPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void crearProducto() {
        Producto producto = new Producto();
        when(productoService.crearProducto(any(Producto.class))).thenReturn(producto);

        ResponseEntity<Producto> response = productoController.crearProducto(producto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(producto, response.getBody());
    }

    @Test
    void actualizarProducto() {
        Producto producto = new Producto();
        when(productoService.actualizarProducto(anyLong(), any(Producto.class))).thenReturn(producto);

        ResponseEntity<Producto> response = productoController.actualizarProducto(1L, producto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(producto, response.getBody());
    }

    @Test
    void actualizarProducto_NotFound() {
        when(productoService.actualizarProducto(anyLong(), any(Producto.class))).thenReturn(null);

        ResponseEntity<Producto> response = productoController.actualizarProducto(1L, new Producto());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void eliminarProducto() {
        ResponseEntity<Void> response = productoController.eliminarProducto(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
