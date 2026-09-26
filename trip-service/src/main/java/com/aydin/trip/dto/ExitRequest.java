package com.aydin.trip.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class ExitRequest {

    @NotNull
    private UUID cardId;

    @NotNull
    private Long stationId;

    public ExitRequest() {
    }

}