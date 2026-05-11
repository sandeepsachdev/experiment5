package com.example.demo.model;

public record Vehicle(
        String vehicleId,
        String tripId,
        String routeId,
        double latitude,
        double longitude,
        float bearing,
        float speed,
        long timestamp,
        String stopId,
        int stopSequence,
        String vehicleStatus   // IN_TRANSIT_TO | STOPPED_AT | INCOMING_AT
) {}
