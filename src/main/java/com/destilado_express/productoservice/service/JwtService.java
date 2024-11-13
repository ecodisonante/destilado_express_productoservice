package com.destilado_express.productoservice.service;

public interface JwtService {
    boolean validateToken(String token);

    String extractUsername(String token);

    String extractRole(String token);
}
