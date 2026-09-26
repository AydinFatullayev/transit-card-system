package com.aydin.trip.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID cardId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "entry_station_id")
    private Station entryStation;

    @ManyToOne
    @JoinColumn(name = "exit_station_id")
    private Station exitStation;

    @Column(nullable = false)
    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status;

    private Integer numberOfStops;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    public Trip(UUID id, UUID cardId, Station entryStation, LocalDateTime entryTime, TripStatus status) {
        this.id = id;
        this.cardId = cardId;
        this.entryStation = entryStation;
        this.entryTime = entryTime;
        this.status = status;
    }

    public Trip(@NotNull UUID cardId, Station entryStation, LocalDateTime entryTime) {
        this.cardId = cardId;
        this.entryStation = entryStation;
        this.entryTime = entryTime;
        this.status = TripStatus.STARTED;
    }

    public void complete(
            Station exitStation,
            LocalDateTime exitTime,
            Integer numberOfStops,
            BigDecimal price
    ) {
        this.exitStation = exitStation;
        this.exitTime = exitTime;
        this.numberOfStops = numberOfStops;
        this.price = price;
        this.status = TripStatus.COMPLETED;
    }

    public void forceComplete(
            LocalDateTime exitTime,
            BigDecimal price
    ) {
        this.exitTime = exitTime;
        this.numberOfStops = null;
        this.price = price;
        this.status = TripStatus.FORCED_COMPLETED;
    }
}