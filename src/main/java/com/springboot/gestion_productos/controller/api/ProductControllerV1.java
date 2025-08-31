package com.springboot.gestion_productos.controller.api;

import com.springboot.gestion_productos.entity.Product;
import com.springboot.gestion_productos.exception.ResourceNotFoundException;
import com.springboot.gestion_productos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/v1/products")
@Hidden
public class ProductControllerV1 {

    @Autowired
    private ProductService productService;

    // GET /api/v1/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return ResponseEntity.ok(product);
    }

    // GET /api/v1/products?page=0&size=5&sort=name,asc&sort=price,desc (Componente 6)
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        return ResponseEntity.ok(products);
    }

    // POST /api/v1/products
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws URISyntaxException {
        if (product.getId() != null) {
            return ResponseEntity.badRequest().build(); // No se puede crear un producto que ya tiene ID
        }
        Product savedProduct = productService.save(product);
        return ResponseEntity.created(new URI("/api/v1/products/" + savedProduct.getId())).body(savedProduct);
    }

    // PUT /api/v1/products/{id} (Idempotente)
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        if (!productService.findById(id).isPresent()) {
             return ResponseEntity.notFound().build();
        }
        product.setId(id); // Asegura que se actualice el recurso correcto
        Product updatedProduct = productService.save(product);
        return ResponseEntity.ok(updatedProduct);
    }
    
    // PATCH /api/v1/products/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Product> partialUpdateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productService.findById(id).map(existingProduct -> {
            if(productDetails.getName() != null) existingProduct.setName(productDetails.getName());
            if(productDetails.getPrice() != null) existingProduct.setPrice(productDetails.getPrice());
            if(productDetails.getStock() != null) existingProduct.setStock(productDetails.getStock());
            Product updatedProduct = productService.save(existingProduct);
            return ResponseEntity.ok(updatedProduct);
        }).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }

    // DELETE /api/v1/products/{id} (Idempotente)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    
    
 // Añade esta línea al controlador para probarlo
    @Value("${app.welcome-message}")
    private String welcomeMessage;

    @GetMapping("/welcome")
    public String welcome() {
        return welcomeMessage; // Devolverá "Bienvenido al Sistema de Gestión de Productos!"
    }
    
    
    
}