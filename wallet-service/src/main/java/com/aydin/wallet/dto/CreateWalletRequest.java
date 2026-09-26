package com.aydin.wallet.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateWalletRequest(@NotNull UUID cardId) {
}