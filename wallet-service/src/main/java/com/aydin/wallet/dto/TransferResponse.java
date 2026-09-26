package com.aydin.wallet.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferResponse(
        UUID sourceCardId,
        UUID targetCardId,
        BigDecimal amount,
        BigDecimal sourceBalance
) {
}