package com.springboot.gestion_productos.dto;

import com.springboot.gestion_productos.annotation.SkuCode;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    
    @NotNull(message = "El SKU es obligatorio")
    @SkuCode // Componente 7: Anotación personalizada
    private String sku;
    
    private String description;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que cero")
    private BigDecimal price;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
    
    private CategoryDTO category;
    private SupplierDTO supplier;
}