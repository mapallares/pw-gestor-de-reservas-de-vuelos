package com.pw.gestorreservasvuelos.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public String test(){
        return "Tienes acceso";
    }
}