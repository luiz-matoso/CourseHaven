package com.luizmatoso.CourseHaven.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error/403")
    public String accessDenied() {
        return "error/403"; // Retorna a página 403.html que você criou
    }
}
