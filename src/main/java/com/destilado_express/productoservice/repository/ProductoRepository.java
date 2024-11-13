package com.destilado_express.productoservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.destilado_express.productoservice.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // buscar solo productos disponibles (para ventas)
    List<Producto> findByDisponible(Boolean disponible);
}
