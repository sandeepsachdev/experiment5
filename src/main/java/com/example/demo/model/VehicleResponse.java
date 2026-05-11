package com.example.demo.model;

import java.util.List;

public record VehicleResponse(
        List<Vehicle> vehicles,
        String lastUpdated,    // ISO-8601
        String status,         // ok | no_api_key | fetch_error
        String message
) {}
