package com.aydin.payment.service;

import com.aydin.payment.dto.CreatePaymentRequest;
import com.aydin.payment.dto.PaymentResponse;
import com.aydin.payment.entity.Payment;
import com.aydin.payment.entity.PaymentStatus;
import com.aydin.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponse createPayment(
            CreatePaymentRequest request
    ) {
        Payment payment = new Payment(
                request.cardId(),
                request.type(),
                request.amount(),
                PaymentStatus.COMPLETED
        );

        paymentRepository.save(payment);

        return PaymentResponse.fromEntity(payment);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByCardId(
            UUID cardId
    ) {
        return paymentRepository
                .findByCardIdOrderByCreatedAtDesc(cardId)
                .stream()
                .map(PaymentResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPayment(UUID id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Payment not found: " + id
                        )
                );

        return PaymentResponse.fromEntity(payment);
    }
}