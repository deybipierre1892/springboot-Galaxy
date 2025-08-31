package com.springboot.gestion_productos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API de Gestión de Productos", version = "1.0", description = "Documentación de la API para el sistema de productos"))
public class OpenApiConfig {
}