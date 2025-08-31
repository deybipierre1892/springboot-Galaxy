package com.springboot.gestion_productos;


import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springdoc.core.utils.SpringDocUtils;

@SpringBootApplication
@EnableScheduling
public class GestionProductosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionProductosApplication.class, args);
    }
    
    // Este bloque soluciona el problema de Swagger con la paginación
    static {
        SpringDocUtils.getConfig().replaceWithClass(Pageable.class, PageableAsQueryParam.class);
    }
}