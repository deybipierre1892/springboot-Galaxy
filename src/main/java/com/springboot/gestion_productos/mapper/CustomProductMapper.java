package com.springboot.gestion_productos.mapper;

import com.springboot.gestion_productos.dto.ProductDTO;
import com.springboot.gestion_productos.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class CustomProductMapper {
    // Ejemplo de un método de mapeo manual simple
    public ProductDTO toDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        // ... mapear otros campos si es necesario
        return dto;
    }
}