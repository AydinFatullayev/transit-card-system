package com.aydin.trip.client;

import com.aydin.trip.dto.WalletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletClient {

    private final RestClient walletRestClient;

    public WalletResponse getWallet(UUID cardId) {

        return walletRestClient
                .get()
                .uri("/api/wallets/{cardId}", cardId)
                .retrieve()
                .body(WalletResponse.class);
    }

    public WalletResponse chargeForTrip(
            UUID cardId,
            BigDecimal fare
    ) {

        return walletRestClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/wallets/{cardId}/charge-trip")
                        .queryParam("fare", fare)
                        .build(cardId)
                )
                .retrieve()
                .body(WalletResponse.class);
    }
}