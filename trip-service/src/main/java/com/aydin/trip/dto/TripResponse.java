package com.aydin.trip.dto;

import com.aydin.trip.entity.Trip;
import com.aydin.trip.entity.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TripResponse {

    private UUID id;
    private UUID cardId;

    private Long entryStationId;
    private String entryStationName;

    private Long exitStationId;
    private String exitStationName;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    private TripStatus status;

    private Integer numberOfStops;
    private BigDecimal price;

    public static TripResponse fromEntity(Trip trip) {

        return new TripResponse(
                trip.getId(),
                trip.getCardId(),

                trip.getEntryStation().getId(),
                trip.getEntryStation().getName(),

                trip.getExitStation() != null
                        ? trip.getExitStation().getId()
                        : null,

                trip.getExitStation() != null
                        ? trip.getExitStation().getName()
                        : null,

                trip.getEntryTime(),
                trip.getExitTime(),
                trip.getStatus(),
                trip.getNumberOfStops(),
                trip.getPrice()
        );
    }

}