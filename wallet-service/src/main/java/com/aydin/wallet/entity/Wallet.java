package com.aydin.wallet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID cardId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal balance;

    public Wallet(UUID cardId) {
        this.cardId = cardId;
        this.balance = BigDecimal.ZERO;
    }

    public void increaseBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void decreaseBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}