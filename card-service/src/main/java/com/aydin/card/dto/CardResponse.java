package com.aydin.card.dto;

import com.aydin.card.entity.Card;
import com.aydin.card.entity.CardStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CardResponse {

    private UUID id;
    private String cardNumber;
    private CardStatus status;

    public CardResponse(UUID id, String cardNumber, CardStatus status) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.status = status;
    }

    public static CardResponse fromEntity(Card card) {
        return new CardResponse(
                card.getId(),
                card.getCardNumber(),
                card.getStatus()
        );
    }

}