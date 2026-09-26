package com.aydin.trip.controller;

import com.aydin.trip.dto.CreateStationRequest;
import com.aydin.trip.dto.StationResponse;
import com.aydin.trip.service.StationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping
    public ResponseEntity<StationResponse> createStation(
            @Valid @RequestBody CreateStationRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(stationService.createStation(request));
    }

    @GetMapping
    public ResponseEntity<List<StationResponse>> getAllStations() {

        return ResponseEntity.ok(
                stationService.getAllStations()
        );
    }
}