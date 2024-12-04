package com.destilado_express.productoservice.service.product;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.destilado_express.productoservice.model.Producto;
import com.destilado_express.productoservice.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    private ProductoRepository productoRepository;
    
    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public List<Producto> getProductosDisponibles() {
        return productoRepository.findByDisponible(true);
    }

    public Producto getProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            productoActualizado.setId(producto.getId());
            return productoRepository.save(productoActualizado);
        }
        return null;
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
