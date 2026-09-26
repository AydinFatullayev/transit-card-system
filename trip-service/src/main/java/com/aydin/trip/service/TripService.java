package com.aydin.trip.service;

import com.aydin.trip.client.WalletClient;
import com.aydin.trip.dto.EntryRequest;
import com.aydin.trip.dto.ExitRequest;
import com.aydin.trip.dto.TripResponse;
import com.aydin.trip.dto.WalletResponse;
import com.aydin.trip.entity.Station;
import com.aydin.trip.entity.Trip;
import com.aydin.trip.entity.TripStatus;
import com.aydin.trip.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TripService {

    private static final BigDecimal PRICE_PER_STOP =
            new BigDecimal("0.30");

    private static final BigDecimal MAX_FARE =
            new BigDecimal("3.00");

    private final TripRepository tripRepository;
    private final StationService stationService;
    private final WalletClient walletClient;

    public TripService(
            TripRepository tripRepository,
            StationService stationService,
            WalletClient walletClient
    ) {
        this.tripRepository = tripRepository;
        this.stationService = stationService;
        this.walletClient = walletClient;
    }

    @Transactional
    public TripResponse startTrip(EntryRequest request) {

        Station entryStation =
                stationService.getStation(request.getStationId());

        tripRepository.findByCardIdAndStatus(
                request.getCardId(),
                TripStatus.STARTED
        ).ifPresent(trip -> {
            throw new IllegalArgumentException(
                    "Card already has an active trip"
            );
        });

        WalletResponse wallet =
                walletClient.getWallet(request.getCardId());

        if (wallet.balance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Entry denied: negative wallet balance"
            );
        }

        Trip trip = new Trip(
                request.getCardId(),
                entryStation,
                LocalDateTime.now()
        );

        tripRepository.save(trip);

        return TripResponse.fromEntity(trip);
    }

    @Transactional
    public TripResponse finishTrip(ExitRequest request) {

        Trip trip = tripRepository
                .findByCardIdAndStatus(
                        request.getCardId(),
                        TripStatus.STARTED
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Active trip not found"
                        )
                );

        Station exitStation =
                stationService.getStation(request.getStationId());

        int numberOfStops = Math.abs(
                exitStation.getSequenceNumber()
                        - trip.getEntryStation().getSequenceNumber()
        );

        BigDecimal price = calculatePrice(numberOfStops);

        walletClient.chargeForTrip(
                request.getCardId(),
                price
        );

        trip.complete(
                exitStation,
                LocalDateTime.now(),
                numberOfStops,
                price
        );

        tripRepository.save(trip);

        return TripResponse.fromEntity(trip);
    }

    private BigDecimal calculatePrice(int numberOfStops) {

        if (numberOfStops == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal price = PRICE_PER_STOP.multiply(
                BigDecimal.valueOf(numberOfStops)
        );

        return price.min(MAX_FARE);
    }

    @Transactional
    public TripResponse forceCompleteTrip(UUID cardId) {

        Trip trip = tripRepository
                .findByCardIdAndStatus(
                        cardId,
                        TripStatus.STARTED
                )
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No active trip found for this card"
                        )
                );

        walletClient.chargeForTrip(
                cardId,
                MAX_FARE
        );

        trip.forceComplete(
                LocalDateTime.now(),
                MAX_FARE
        );

        return TripResponse.fromEntity(
                tripRepository.save(trip)
        );
    }

    @Transactional(readOnly = true)
    public TripResponse getTrip(UUID id) {

        Trip trip = tripRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Trip not found: " + id
                        )
                );

        return TripResponse.fromEntity(trip);
    }
}