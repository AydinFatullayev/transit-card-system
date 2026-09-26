package com.aydin.wallet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "wallet_transactions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID walletId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public WalletTransaction(
            UUID walletId,
            TransactionType type,
            BigDecimal amount
    ) {
        this.walletId = walletId;
        this.type = type;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }
}