package com.springboot.gestion_productos.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SkuCodeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SkuCode {
    String message() default "El formato del SKU no es válido. Debe ser como ABC-DE-123.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}