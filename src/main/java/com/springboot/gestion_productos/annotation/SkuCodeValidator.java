package com.springboot.gestion_productos.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SkuCodeValidator implements ConstraintValidator<SkuCode, String> {

    @Override
    public void initialize(SkuCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String sku, ConstraintValidatorContext context) {
        if (sku == null) {
            return false;
        }
        // Expresión para validar el formato. Ejemplo: LP-TC-001
        return sku.matches("[A-Z]{2}-[A-Z]{2}-\\d{3}");
    }
}