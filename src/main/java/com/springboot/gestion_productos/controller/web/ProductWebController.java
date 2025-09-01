package com.springboot.gestion_productos.controller.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.gestion_productos.dto.ProductDTO;
import com.springboot.gestion_productos.repository.CategoryRepository;
import com.springboot.gestion_productos.repository.SupplierRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/web/products")
public class ProductWebController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private final String API_URL = "http://localhost:8080/api/v2/products";

    private void loadDropdownData(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
    }

    @GetMapping
    public String listProducts(Model model) {
        try {
            ResponseEntity<RestResponsePage<ProductDTO>> response = restTemplate.exchange(
                API_URL + "?sort=id,asc",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<RestResponsePage<ProductDTO>>() {}
            );
            model.addAttribute("products", response.getBody().getContent());
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al cargar productos: " + e.getMessage());
        }
        return "list-products"; // Redirige a list-products.html
    }

    @GetMapping("/new")
    public String showFormForAdd(Model model) {
        model.addAttribute("product", new ProductDTO());
        loadDropdownData(model);
        return "form-product";
    }

    @GetMapping("/edit/{id}")
    public String showFormForUpdate(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<ProductDTO> response = restTemplate.getForEntity(API_URL + "/" + id, ProductDTO.class);
            model.addAttribute("product", response.getBody());
            loadDropdownData(model);
            return "form-product";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado.");
            return "redirect:/web/products";
        }
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") ProductDTO productDTO,
                              BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            loadDropdownData(model);
            return "form-product";
        }

        try {
            if (productDTO.getId() == null) {
                restTemplate.postForEntity(API_URL, productDTO, ProductDTO.class);
                redirectAttributes.addFlashAttribute("success", "Producto creado exitosamente.");
            } else {
                restTemplate.put(API_URL + "/" + productDTO.getId(), productDTO);
                redirectAttributes.addFlashAttribute("success", "Producto actualizado exitosamente.");
            }
        } catch (HttpClientErrorException e) {
            String responseBody = e.getResponseBodyAsString();
            try {
                Map<String, String> errors = objectMapper.readValue(responseBody, new TypeReference<>() {});
                model.addAttribute("apiErrors", errors);
            } catch (Exception jsonException) {
                model.addAttribute("apiErrors", Map.of("general", "Error inesperado del servidor."));
            }
            loadDropdownData(model);
            return "form-product";
        }

        return "redirect:/web/products";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            restTemplate.delete(API_URL + "/" + id);
            redirectAttributes.addFlashAttribute("success", "Producto eliminado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto.");
        }
        return "redirect:/web/products";
    }
}