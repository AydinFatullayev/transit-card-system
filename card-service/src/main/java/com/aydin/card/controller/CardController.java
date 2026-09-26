package com.aydin.card.controller;

import com.aydin.card.dto.CardResponse;
import com.aydin.card.dto.CreateCardRequest;
import com.aydin.card.service.CardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardResponse> createCard(
            @Valid @RequestBody CreateCardRequest request
    ) {

        CardResponse response = cardService.createCard(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCard(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                cardService.getCard(id)
        );
    }

    @GetMapping("/number/{cardNumber}")
    public ResponseEntity<CardResponse> getCardByNumber(
            @PathVariable String cardNumber
    ) {

        return ResponseEntity.ok(
                cardService.getCardByNumber(cardNumber)
        );
    }

    @GetMapping
    public ResponseEntity<List<CardResponse>> getAllCards() {

        return ResponseEntity.ok(
                cardService.getAllCards()
        );
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<CardResponse> blockCard(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                cardService.blockCard(id)
        );
    }
}