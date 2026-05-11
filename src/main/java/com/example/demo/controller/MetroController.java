package com.example.demo.controller;

import com.example.demo.model.Station;
import com.example.demo.model.VehicleResponse;
import com.example.demo.service.MetroDataService;
import com.example.demo.service.StationRegistry;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class MetroController {

    private final MetroDataService metroDataService;
    private final StationRegistry stationRegistry;

    public MetroController(MetroDataService metroDataService, StationRegistry stationRegistry) {
        this.metroDataService = metroDataService;
        this.stationRegistry  = stationRegistry;
    }

    @GetMapping("/")
    public void root(HttpServletResponse response) throws IOException {
        response.sendRedirect("/index.html");
    }

    @GetMapping("/api/metro/vehicles")
    public VehicleResponse vehicles() {
        return metroDataService.latest();
    }

    @GetMapping("/api/metro/stations")
    public List<Station> stations() {
        return stationRegistry.all();
    }

    @GetMapping("/api/metro/lines")
    public Map<String, List<String>> lines() {
        return Map.of(
            "northwest", StationRegistry.NORTHWEST_ORDER,
            "city",      StationRegistry.CITY_ORDER
        );
    }
}
