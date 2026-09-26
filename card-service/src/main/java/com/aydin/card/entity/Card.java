package com.aydin.card.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 32)
    private String cardNumber;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status;

    public Card(){

    }
    public Card(UUID id, String cardNumber, CardStatus status) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.status = status;
    }

}