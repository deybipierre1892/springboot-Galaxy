package com.springboot.gestion_productos.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Devuelve el nombre del archivo HTML (login.html)
    }
}