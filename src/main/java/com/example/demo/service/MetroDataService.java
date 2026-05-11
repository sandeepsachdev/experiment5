package com.example.demo.service;

import com.example.demo.model.Vehicle;
import com.example.demo.model.VehicleResponse;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;
import com.google.transit.realtime.GtfsRealtime.VehiclePosition;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MetroDataService {

    private static final Logger log = LoggerFactory.getLogger(MetroDataService.class);

    // Sydney Metro corridor bounding box (Tallawong → Sydenham)
    private static final double MIN_LAT = -34.0, MAX_LAT = -33.4;
    private static final double MIN_LON = 150.7, MAX_LON = 151.5;

    @Value("${transport.nsw.api.key:}")
    private String apiKey;

    @Value("${transport.nsw.vehicle.url:https://api.transport.nsw.gov.au/v2/gtfs/vehiclepos/metro}")
    private String vehicleUrl;

    private final RestClient restClient;
    private final AtomicReference<VehicleResponse> cache =
        new AtomicReference<>(new VehicleResponse(List.of(), null, "starting", "Initialising…"));

    public MetroDataService(RestClient restClient) {
        this.restClient = restClient;
    }

    @PostConstruct
    void init() { fetchAndCache(); }

    @Scheduled(fixedDelay = 30_000)
    void refresh() { fetchAndCache(); }

    public VehicleResponse latest() { return cache.get(); }

    private void fetchAndCache() {
        if (apiKey == null || apiKey.isBlank()) {
            cache.set(new VehicleResponse(
                List.of(), Instant.now().toString(), "no_api_key",
                "Set the TRANSPORT_NSW_API_KEY environment variable to see live trains."
            ));
            return;
        }

        try {
            byte[] body = restClient.get()
                    .uri(vehicleUrl)
                    .header("Authorization", "apikey " + apiKey)
                    .retrieve()
                    .body(byte[].class);

            if (body == null || body.length == 0) {
                log.warn("Empty response from Transport NSW API");
                cache.set(new VehicleResponse(List.of(), Instant.now().toString(),
                    "fetch_error", "Empty response from Transport NSW API."));
                return;
            }

            FeedMessage feed = FeedMessage.parseFrom(body);
            List<Vehicle> vehicles = new ArrayList<>();

            for (var entity : feed.getEntityList()) {
                if (!entity.hasVehicle()) continue;
                VehiclePosition vp = entity.getVehicle();
                if (!vp.hasPosition()) continue;

                double lat = vp.getPosition().getLatitude();
                double lon = vp.getPosition().getLongitude();
                if (lat == 0 && lon == 0) continue;
                if (lat < MIN_LAT || lat > MAX_LAT || lon < MIN_LON || lon > MAX_LON) continue;

                vehicles.add(new Vehicle(
                        vp.hasVehicle() ? vp.getVehicle().getId() : entity.getId(),
                        vp.hasTrip()    ? vp.getTrip().getTripId()  : "",
                        vp.hasTrip()    ? vp.getTrip().getRouteId() : "",
                        lat, lon,
                        vp.getPosition().getBearing(),
                        vp.getPosition().getSpeed(),
                        vp.getTimestamp(),
                        vp.getStopId(),
                        vp.getCurrentStopSequence(),
                        vp.getCurrentStatus().name()
                ));
            }

            log.info("Fetched {} metro vehicles", vehicles.size());
            cache.set(new VehicleResponse(vehicles, Instant.now().toString(), "ok",
                vehicles.size() + " active trains"));

        } catch (Exception e) {
            log.error("Failed to fetch vehicle positions: {}", e.getMessage());
            VehicleResponse prev = cache.get();
            cache.set(new VehicleResponse(
                prev.vehicles(), Instant.now().toString(), "fetch_error",
                "Fetch failed: " + e.getMessage()
            ));
        }
    }
}
