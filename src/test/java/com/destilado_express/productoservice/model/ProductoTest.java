package com.destilado_express.productoservice.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.destilado_express.productoservice.model.Producto;

class ProductoTest {

    private Producto producto;

    @BeforeEach
    void SetUp() {
        producto = new Producto(null, null, null, 0, 0, 0, null);
    }

    @Test
    void onCreateTest() {
        // act
        producto.onCreate();

        // assert
        assertNotNull(producto.getFechaCreacion());
        assertTrue(producto.getFechaCreacion().isBefore(LocalDateTime.now())
                || producto.getFechaCreacion().isEqual(LocalDateTime.now()));
    }

    @Test
    void onUpdateTest() {
        // act
        producto.onUpdate();

        // assert
        assertNotNull(producto.getFechaActualizacion());
        assertTrue(producto.getFechaActualizacion().isBefore(LocalDateTime.now())
                || producto.getFechaActualizacion().isEqual(LocalDateTime.now()));
    }

}
