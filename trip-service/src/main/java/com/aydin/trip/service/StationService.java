package com.aydin.trip.service;

import com.aydin.trip.dto.CreateStationRequest;
import com.aydin.trip.dto.StationResponse;
import com.aydin.trip.entity.Station;
import com.aydin.trip.repository.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StationService {

    private final StationRepository stationRepository;
    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Transactional
    public StationResponse createStation(CreateStationRequest request) {

        if (stationRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException(
                    "Station with this name already exists"
            );
        }

        if (stationRepository.existsBySequenceNumber(
                request.getSequenceNumber()
        )) {
            throw new IllegalArgumentException(
                    "Station with this sequence number already exists"
            );
        }

        Station station = new Station(
                null,
                request.getName(),
                request.getSequenceNumber()
        );

        return StationResponse.fromEntity(
                stationRepository.save(station)
        );
    }

    @Transactional(readOnly = true)
    public List<StationResponse> getAllStations() {

        return stationRepository.findAll()
                .stream()
                .sorted(
                        (a, b) -> Integer.compare(
                                a.getSequenceNumber(),
                                b.getSequenceNumber()
                        )
                )
                .map(StationResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public Station getStation(Long id) {

        return stationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Station not found: " + id
                        )
                );
    }
}