package com.aydin.wallet.repository;

import com.aydin.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    Optional<Wallet> findByCardId(UUID cardId);

    boolean existsByCardId(UUID cardId);
}