package com.aydin.card.repository;

import com.aydin.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {

    Optional<Card> findByCardNumber(String cardNumber);
    boolean existsByCardNumber(String cardNumber);

}