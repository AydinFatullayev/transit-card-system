package com.aydin.trip.dto;

import com.aydin.trip.entity.Station;
import lombok.Getter;

@Getter
public class StationResponse {

    private Long id;
    private String name;
    private Integer sequenceNumber;

    public StationResponse(
            Long id,
            String name,
            Integer sequenceNumber
    ) {
        this.id = id;
        this.name = name;
        this.sequenceNumber = sequenceNumber;
    }

    public static StationResponse fromEntity(Station station) {
        return new StationResponse(
                station.getId(),
                station.getName(),
                station.getSequenceNumber()
        );
    }

}