package com.aydin.wallet.service;

import com.aydin.wallet.dto.*;
import com.aydin.wallet.entity.TransactionType;
import com.aydin.wallet.entity.Wallet;
import com.aydin.wallet.entity.WalletTransaction;
import com.aydin.wallet.repository.WalletRepository;
import com.aydin.wallet.repository.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private static final BigDecimal TRIP_PENALTY = new BigDecimal("0.20");

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    @Transactional
    public WalletResponse createWallet(CreateWalletRequest request) {

        if (walletRepository.existsByCardId(request.cardId())) {
            throw new IllegalArgumentException("Wallet already exists for this card");
        }

        Wallet wallet = new Wallet(request.cardId());

        walletRepository.save(wallet);

        return WalletResponse.fromEntity(wallet);
    }

    public WalletResponse getWalletByCardId(UUID cardId) {

        Wallet wallet = getWallet(cardId);

        return WalletResponse.fromEntity(wallet);
    }

    @Transactional
    public WalletResponse topUp(UUID cardId, AmountRequest request) {

        Wallet wallet = getWallet(cardId);

        wallet.increaseBalance(request.amount());

        walletRepository.save(wallet);

        WalletTransaction transaction = new WalletTransaction(
                wallet.getId(),
                TransactionType.TOP_UP,
                request.amount()
        );

        walletTransactionRepository.save(transaction);

        return WalletResponse.fromEntity(wallet);
    }

    @Transactional
    public WalletResponse withdraw(UUID cardId, AmountRequest request) {

        Wallet wallet = getWallet(cardId);

        checkSufficientBalance(wallet, request.amount());

        wallet.decreaseBalance(request.amount());

        walletRepository.save(wallet);

        WalletTransaction transaction = new WalletTransaction(
                wallet.getId(),
                TransactionType.WITHDRAW,
                request.amount()
        );

        walletTransactionRepository.save(transaction);

        return WalletResponse.fromEntity(wallet);
    }

    @Transactional
    public TransferResponse transfer(
            UUID sourceCardId,
            TransferRequest request
    ) {

        if (sourceCardId.equals(request.targetCardId())) {
            throw new IllegalArgumentException(
                    "Source and target cards must be different"
            );
        }

        Wallet sourceWallet = getWallet(sourceCardId);
        Wallet targetWallet = getWallet(request.targetCardId());

        checkSufficientBalance(sourceWallet, request.amount());

        sourceWallet.decreaseBalance(request.amount());
        targetWallet.increaseBalance(request.amount());

        walletRepository.save(sourceWallet);
        walletRepository.save(targetWallet);

        WalletTransaction outgoingTransaction = new WalletTransaction(
                sourceWallet.getId(),
                TransactionType.TRANSFER_OUT,
                request.amount()
        );

        WalletTransaction incomingTransaction = new WalletTransaction(
                targetWallet.getId(),
                TransactionType.TRANSFER_IN,
                request.amount()
        );

        walletTransactionRepository.save(outgoingTransaction);
        walletTransactionRepository.save(incomingTransaction);

        return new TransferResponse(
                sourceCardId,
                request.targetCardId(),
                request.amount(),
                sourceWallet.getBalance()
        );
    }
    @Transactional
    public WalletResponse chargeForTrip(UUID cardId, BigDecimal fare) {

        Wallet wallet = getWallet(cardId);

        BigDecimal amountToCharge = fare;

        /*
         * Если денег недостаточно для поездки,
         * добавляем штраф 0.20 AZN.
         */
        if (wallet.getBalance().compareTo(fare) < 0) {
            amountToCharge = fare.add(TRIP_PENALTY);
        }

        wallet.decreaseBalance(amountToCharge);

        walletRepository.save(wallet);

        WalletTransaction transaction = new WalletTransaction(
                wallet.getId(),
                TransactionType.TRIP_PAYMENT,
                amountToCharge
        );

        walletTransactionRepository.save(transaction);

        return WalletResponse.fromEntity(wallet);
    }

    private Wallet getWallet(UUID cardId) {

        return walletRepository.findByCardId(cardId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Wallet not found for card: " + cardId
                        )
                );
    }

    private void checkSufficientBalance(
            Wallet wallet,
            BigDecimal amount
    ) {

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Insufficient balance"
            );
        }
    }
}