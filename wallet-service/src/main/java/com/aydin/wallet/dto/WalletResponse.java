package com.aydin.wallet.dto;

import com.aydin.wallet.entity.Wallet;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse(
        UUID id,
        UUID cardId,
        BigDecimal balance
) {

    public static WalletResponse fromEntity(Wallet wallet) {
        return new WalletResponse(
                wallet.getId(),
                wallet.getCardId(),
                wallet.getBalance()
        );
    }
}