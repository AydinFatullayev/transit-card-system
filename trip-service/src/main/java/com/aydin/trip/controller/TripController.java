package com.aydin.trip.controller;

import com.aydin.trip.dto.EntryRequest;
import com.aydin.trip.dto.ExitRequest;
import com.aydin.trip.dto.TripResponse;
import com.aydin.trip.service.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/entry")
    public ResponseEntity<TripResponse> startTrip(
            @Valid @RequestBody EntryRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tripService.startTrip(request));
    }

    @PostMapping("/exit")
    public ResponseEntity<TripResponse> finishTrip(
            @Valid @RequestBody ExitRequest request
    ) {

        return ResponseEntity.ok(
                tripService.finishTrip(request)
        );
    }

    @PostMapping("/{cardId}/force-complete")
    public ResponseEntity<TripResponse> forceCompleteTrip(
            @PathVariable UUID cardId
    ) {

        return ResponseEntity.ok(
                tripService.forceCompleteTrip(cardId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponse> getTrip(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                tripService.getTrip(id)
        );
    }
}