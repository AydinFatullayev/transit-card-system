package com.aydin.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID cardId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Payment(
            UUID cardId,
            PaymentType type,
            BigDecimal amount,
            PaymentStatus status
    ) {
        this.cardId = cardId;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
}