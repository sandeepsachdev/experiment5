package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
                "service", "spring-boot-demo",
                "status", "ok",
                "timestamp", Instant.now().toString()
        );
    }
}
