package com.springboot.gestion_productos.controller.api;

import com.springboot.gestion_productos.dto.ProductDTO;
import com.springboot.gestion_productos.entity.Product;
import com.springboot.gestion_productos.exception.ResourceNotFoundException;
import com.springboot.gestion_productos.mapper.ProductMapper;
import com.springboot.gestion_productos.service.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v2/products")
public class ProductControllerV2 {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper; // Inyectando MapStruct

    @Autowired
    private ModelMapper modelMapper; // Inyectando ModelMapper
    
    // GET /api/v2/products?page=0&size=5&sort=name,asc (Componente 6 con DTO)
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productService.findAll(pageable);
        // Mapeamos la página de Entidades a una página de DTOs usando ModelMapper
        Page<ProductDTO> productDTOPage = productPage.map(product -> modelMapper.map(product, ProductDTO.class));
        return ResponseEntity.ok(productDTOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        // Usando MapStruct para convertir Entidad a DTO
        return ResponseEntity.ok(productMapper.productToProductDTO(product));
    }
    
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        // Usando MapStruct para convertir DTO a Entidad
        Product product = productMapper.productDTOToProduct(productDTO);
        Product savedProduct = productService.save(product);
        ProductDTO savedProductDTO = productMapper.productToProductDTO(savedProduct);
        return ResponseEntity.created(new URI("/api/v2/products/" + savedProduct.getId())).body(savedProductDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        return productService.findById(id)
            .map(product -> {
                Product productToUpdate = productMapper.productDTOToProduct(productDTO);
                productToUpdate.setId(id); // Asegurar el ID
                Product updatedProduct = productService.save(productToUpdate);
                return ResponseEntity.ok(productMapper.productToProductDTO(updatedProduct));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}