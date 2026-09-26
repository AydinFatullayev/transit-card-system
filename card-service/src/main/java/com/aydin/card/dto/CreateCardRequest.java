package com.aydin.card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateCardRequest {

    @NotBlank
    @Size(min = 8, max = 32)
    private String cardNumber;

    public CreateCardRequest() {
    }

}