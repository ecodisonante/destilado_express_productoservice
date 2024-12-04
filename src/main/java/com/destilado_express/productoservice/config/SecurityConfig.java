package com.destilado_express.productoservice.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtRequestFilter jwtRequestFilter; // Reutiliza o adapta el filtro de JWT
    private String productosApi = "/api/productos";
    private String adminRole = "ADMIN";

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests(req -> req
                        // acceso publico
                        .requestMatchers(HttpMethod.GET, productosApi).permitAll()
                        // solo registrados
                        .requestMatchers(HttpMethod.GET, productosApi + "/**").authenticated()
                        // solo admin
                        .requestMatchers(HttpMethod.PUT, productosApi).hasRole(adminRole)
                        .requestMatchers(HttpMethod.POST, productosApi).hasRole(adminRole)
                        .requestMatchers(HttpMethod.DELETE, productosApi + "/**").hasRole(adminRole)
                        // otros
                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("http://localhost");
        config.addAllowedOriginPattern("http://localhost:*");
        config.addAllowedOriginPattern("http://frontend");
        config.addAllowedOriginPattern("http://frontend:*");
        config.addAllowedHeader("*");
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
