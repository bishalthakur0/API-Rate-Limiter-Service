package com.example.ratelimiter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/resource")
    public String getResource() {
        return "Success: Request was allowed.";
    }
}
