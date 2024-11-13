package com.destilado_express.productoservice.service;

import com.destilado_express.productoservice.model.Producto;
import java.util.List;

public interface ProductoService {

    public List<Producto> getAllProductos();

    public List<Producto> getProductosDisponibles();

    public Producto getProductoById(Long id);

    public Producto crearProducto(Producto producto);

    public Producto actualizarProducto(Long id, Producto productoActualizado);

    public void eliminarProducto(Long id);
}
