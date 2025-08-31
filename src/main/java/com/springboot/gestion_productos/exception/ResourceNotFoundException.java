package com.springboot.gestion_productos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada para indicar que un recurso solicitado no fue encontrado.
 * Al ser lanzada desde un controlador, Spring automáticamente responderá con un
 * estado HTTP 404 Not Found gracias a la anotación @ResponseStatus.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Un identificador de versión único para la serialización.
     * Es una buena práctica incluirlo en las clases de excepción.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor que acepta un mensaje detallando el error.
     * @param message El mensaje que describe por qué se lanzó la excepción.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
