package com.aydin.card.service;

import com.aydin.card.dto.CardResponse;
import com.aydin.card.dto.CreateCardRequest;
import com.aydin.card.entity.Card;
import com.aydin.card.entity.CardStatus;
import com.aydin.card.repository.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    public CardResponse createCard(CreateCardRequest request) {

        if (cardRepository.existsByCardNumber(request.getCardNumber())) {
            throw new IllegalArgumentException(
                    "Card with number " + request.getCardNumber() + " already exists"
            );
        }

        Card card = new Card(
                null,
                request.getCardNumber(),
                CardStatus.ACTIVE
        );

        Card savedCard = cardRepository.save(card);

        return CardResponse.fromEntity(savedCard);
    }

    @Transactional(readOnly = true)
    public CardResponse getCard(UUID id) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Card not found: " + id)
                );

        return CardResponse.fromEntity(card);
    }

    @Transactional(readOnly = true)
    public CardResponse getCardByNumber(String cardNumber) {

        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Card not found: " + cardNumber
                        )
                );

        return CardResponse.fromEntity(card);
    }

    @Transactional(readOnly = true)
    public List<CardResponse> getAllCards() {

        return cardRepository.findAll()
                .stream()
                .map(CardResponse::fromEntity)
                .toList();
    }

    @Transactional
    public CardResponse blockCard(UUID id) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Card not found: " + id)
                );

        card.setStatus(CardStatus.BLOCKED);

        return CardResponse.fromEntity(cardRepository.save(card));
    }
}