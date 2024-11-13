package com.destilado_express.productoservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.destilado_express.productoservice.model.Producto;
import com.destilado_express.productoservice.repository.ProductoRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {

        // Poblar Producto
        if (productoRepository.count() == 0) {
            productoRepository
                    .save(new Producto("Whisky", "Whisky escocés premium", "imagen_whisky.jpg", 15000, 0, 10, true));
            productoRepository
                    .save(new Producto("Vodka", "Vodka ruso tradicional", "imagen_vodka.jpg", 13000, 0, 20, true));
            productoRepository.save(new Producto("Ron", "Ron añejo cubano", "imagen_ron.jpg", 12000, 0, 15, true));
        }

    }
}
