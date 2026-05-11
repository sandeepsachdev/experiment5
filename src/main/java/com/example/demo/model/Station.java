package com.example.demo.model;

public record Station(
        String id,
        String name,
        double lat,
        double lon,
        String line,
        boolean interchange
) {}
