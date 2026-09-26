package com.aydin.wallet.controller;

import com.aydin.wallet.dto.*;
import com.aydin.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<WalletResponse> createWallet(
            @Valid @RequestBody CreateWalletRequest request
    ) {

        return ResponseEntity.ok(
                walletService.createWallet(request)
        );
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<WalletResponse> getWallet(
            @PathVariable UUID cardId
    ) {

        return ResponseEntity.ok(
                walletService.getWalletByCardId(cardId)
        );
    }

    @PostMapping("/{cardId}/top-up")
    public ResponseEntity<WalletResponse> topUp(
            @PathVariable UUID cardId,
            @Valid @RequestBody AmountRequest request
    ) {

        return ResponseEntity.ok(
                walletService.topUp(cardId, request)
        );
    }

    @PostMapping("/{cardId}/withdraw")
    public ResponseEntity<WalletResponse> withdraw(
            @PathVariable UUID cardId,
            @Valid @RequestBody AmountRequest request
    ) {

        return ResponseEntity.ok(
                walletService.withdraw(cardId, request)
        );
    }

    @PostMapping("/{cardId}/transfer")
    public ResponseEntity<TransferResponse> transfer(
            @PathVariable UUID cardId,
            @Valid @RequestBody TransferRequest request
    ) {

        return ResponseEntity.ok(
                walletService.transfer(cardId, request)
        );
    }

    @PostMapping("/{cardId}/charge-trip")
    public ResponseEntity<WalletResponse> chargeForTrip(
            @PathVariable UUID cardId,
            @RequestParam java.math.BigDecimal fare
    ) {

        return ResponseEntity.ok(
                walletService.chargeForTrip(cardId, fare)
        );
    }
}