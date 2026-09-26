package com.aydin.payment.controller;

import com.aydin.payment.dto.CreatePaymentRequest;
import com.aydin.payment.dto.PaymentResponse;
import com.aydin.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody CreatePaymentRequest request
    ) {
        return ResponseEntity.ok(
                paymentService.createPayment(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                paymentService.getPayment(id)
        );
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByCard(
            @PathVariable UUID cardId
    ) {
        return ResponseEntity.ok(
                paymentService.getPaymentsByCardId(cardId)
        );
    }
}