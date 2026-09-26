package com.aydin.trip.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse(
        UUID id,
        UUID cardId,
        BigDecimal balance
) {
}