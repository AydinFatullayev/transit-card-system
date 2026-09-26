package com.aydin.payment.dto;

import com.aydin.payment.entity.PaymentType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentRequest(

        @NotNull
        UUID cardId,

        @NotNull
        PaymentType type,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal amount
) {
}