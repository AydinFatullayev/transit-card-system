package com.aydin.trip.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStationRequest {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Integer sequenceNumber;

    public CreateStationRequest() {
    }

}