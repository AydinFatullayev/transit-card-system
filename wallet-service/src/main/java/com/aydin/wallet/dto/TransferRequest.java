package com.aydin.wallet.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(

        @NotNull
        UUID targetCardId,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount

) {
}