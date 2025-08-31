package com.springboot.gestion_productos.service.impl;

import com.springboot.gestion_productos.entity.Product;
import com.springboot.gestion_productos.repository.ProductRepository;
import com.springboot.gestion_productos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Cacheable("products") // Componente 9: Anotación @Cacheable
    public Optional<Product> findById(Long id) {
        System.out.println("Buscando producto en la BD: " + id); // Para demostrar que la caché funciona
        return productRepository.findById(id);
    }
    
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
