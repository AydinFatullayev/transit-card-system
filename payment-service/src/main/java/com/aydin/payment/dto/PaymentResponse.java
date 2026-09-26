package com.aydin.payment.dto;

import com.aydin.payment.entity.Payment;
import com.aydin.payment.entity.PaymentStatus;
import com.aydin.payment.entity.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID cardId,
        PaymentType type,
        BigDecimal amount,
        PaymentStatus status,
        LocalDateTime createdAt
) {

    public static PaymentResponse fromEntity(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getCardId(),
                payment.getType(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt()
        );
    }
}