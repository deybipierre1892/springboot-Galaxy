package com.springboot.gestion_productos.mapper;

import com.springboot.gestion_productos.dto.ProductDTO;
import com.springboot.gestion_productos.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    
    @Mapping(source = "category.name", target = "category.name")
    @Mapping(source = "supplier.name", target = "supplier.name")
    ProductDTO productToProductDTO(Product product);

    @Mapping(target = "creationDate", ignore = true) 
    // LÍNEAS AÑADIDAS PARA SOLUCIONAR EL WARNING
    @Mapping(target = "supplier.contactPerson", ignore = true)
    @Mapping(target = "supplier.products", ignore = true)
    @Mapping(target = "category.products", ignore = true) // Es buena idea ignorar este también
    Product productDTOToProduct(ProductDTO productDTO);
}