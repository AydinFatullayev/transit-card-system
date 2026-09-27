package com.aydin.wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID cardId,
        String type,
        BigDecimal amount,
        String status,
        LocalDateTime createdAt
) {
}