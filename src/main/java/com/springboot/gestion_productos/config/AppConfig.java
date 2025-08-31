package com.springboot.gestion_productos.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration(proxyBeanMethods = false) // <-- MODO LITE AÑADIDO
public class AppConfig {

    // Bean para ModelMapper
    @Bean
    ModelMapper modelMapper() { 
        return new ModelMapper();
    }

    // Bean para RestTemplate
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) { 
        return builder
                .basicAuthentication("user", "password")
                .build();
    }
    
    // Ejemplo de @Primary y @Qualifier
    @Bean
    @Primary
    String primaryGreeting() { 
        return "Hola desde el Bean Primario!";
    }

    @Bean
    @Qualifier("secondary")
    String secondaryGreeting() { 
        return "Hola desde el Bean Secundario!";
    }
}