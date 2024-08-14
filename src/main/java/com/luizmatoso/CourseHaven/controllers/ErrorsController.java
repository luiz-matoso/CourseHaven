package com.luizmatoso.CourseHaven.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorsController{

    @GetMapping("/error/403")
    public String accessDenied() {
        return "error/403"; 
    }
}
