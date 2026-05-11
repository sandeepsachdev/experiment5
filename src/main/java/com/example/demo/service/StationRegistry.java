package com.example.demo.service;

import com.example.demo.model.Station;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StationRegistry {

    // Coordinates sourced from Transport NSW GTFS static feed
    private static final List<Station> STATIONS = List.of(
        // ── Northwest Line ────────────────────────────────────────────────────
        new Station("tallawong",        "Tallawong",             -33.689, 150.910, "northwest", false),
        new Station("rouse-hill",       "Rouse Hill",            -33.685, 150.918, "northwest", false),
        new Station("kellyville",       "Kellyville",            -33.700, 150.937, "northwest", false),
        new Station("bella-vista",      "Bella Vista",           -33.706, 150.944, "northwest", false),
        new Station("norwest",          "Norwest",               -33.715, 150.954, "northwest", false),
        new Station("hills-showground", "Hills Showground",      -33.729, 150.972, "northwest", false),
        new Station("castle-hill",      "Castle Hill",           -33.730, 151.004, "northwest", false),
        new Station("cherrybrook",      "Cherrybrook",           -33.751, 151.011, "northwest", false),
        new Station("epping",           "Epping",                -33.772, 151.082, "northwest", true),
        new Station("macquarie-uni",    "Macquarie University",  -33.774, 151.117, "northwest", false),
        new Station("macquarie-park",   "Macquarie Park",        -33.778, 151.123, "northwest", false),
        new Station("north-ryde",       "North Ryde",            -33.800, 151.133, "northwest", false),
        new Station("chatswood",        "Chatswood",             -33.797, 151.183, "city",      true),

        // ── City & Southwest ─────────────────────────────────────────────────
        new Station("crows-nest",       "Crows Nest",            -33.827, 151.207, "city", false),
        new Station("victoria-cross",   "Victoria Cross",        -33.839, 151.210, "city", false),
        new Station("barangaroo",       "Barangaroo",            -33.860, 151.200, "city", false),
        new Station("martin-place",     "Martin Place",          -33.868, 151.210, "city", true),
        new Station("gadigal",          "Gadigal",               -33.876, 151.207, "city", false),
        new Station("central",          "Central",               -33.884, 151.206, "city", true),
        new Station("waterloo",         "Waterloo",              -33.897, 151.203, "city", false),
        new Station("sydenham",         "Sydenham",              -33.918, 151.170, "city", true)
    );

    private static final Map<String, Station> BY_ID =
        STATIONS.stream().collect(Collectors.toMap(Station::id, s -> s));

    public List<Station> all()         { return STATIONS; }
    public Map<String, Station> byId() { return BY_ID; }

    public static final List<String> NORTHWEST_ORDER = List.of(
        "tallawong","rouse-hill","kellyville","bella-vista","norwest","hills-showground",
        "castle-hill","cherrybrook","epping","macquarie-uni",
        "macquarie-park","north-ryde","chatswood"
    );
    public static final List<String> CITY_ORDER = List.of(
        "chatswood","crows-nest","victoria-cross","barangaroo",
        "martin-place","gadigal","central","waterloo","sydenham"
    );
}
