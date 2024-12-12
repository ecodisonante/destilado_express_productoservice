package com.destilado_express.productoservice.service.product;

import com.destilado_express.productoservice.model.Producto;
import com.destilado_express.productoservice.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProductos() {
        List<Producto> productos = List.of(new Producto());
        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> result = productoService.getAllProductos();

        assertEquals(productos, result);
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void getProductosDisponibles() {
        List<Producto> productos = List.of(new Producto());
        when(productoRepository.findByDisponible(true)).thenReturn(productos);

        List<Producto> result = productoService.getProductosDisponibles();

        assertEquals(productos, result);
        verify(productoRepository, times(1)).findByDisponible(true);
    }

    @Test
    void getProductoById() {
        Producto producto = new Producto();
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));

        Producto result = productoService.getProductoById(1L);

        assertEquals(producto, result);
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void getProductoById_NotFound() {
        when(productoRepository.findById(anyLong())).thenReturn(Optional.empty());

        Producto result = productoService.getProductoById(1L);

        assertNull(result);
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void crearProducto() {
        Producto producto = new Producto();
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto result = productoService.crearProducto(producto);

        assertEquals(producto, result);
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void actualizarProducto() {
        Producto producto = new Producto();
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto result = productoService.actualizarProducto(1L, producto);

        assertEquals(producto, result);
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void actualizarProducto_NotFound() {
        when(productoRepository.findById(anyLong())).thenReturn(Optional.empty());

        Producto result = productoService.actualizarProducto(1L, new Producto());

        assertNull(result);
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void eliminarProducto() {
        doNothing().when(productoRepository).deleteById(anyLong());

        productoService.eliminarProducto(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }
}
