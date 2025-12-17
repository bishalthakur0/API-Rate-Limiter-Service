package com.example.ratelimiter.controller;

import com.example.ratelimiter.model.RateLimitEntry;
import com.example.ratelimiter.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173") // Allow React Frontend
public class DashboardController {

    @Autowired
    private RateLimiterService rateLimiterService;

    @GetMapping("/api/usage")
    public List<RateLimitEntry> getUsageStats() {
        return rateLimiterService.getUsageStats();
    }
}
