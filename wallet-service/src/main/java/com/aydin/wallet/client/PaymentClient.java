package com.aydin.wallet.client;

import com.aydin.wallet.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentClient {

    private final RestClient paymentRestClient;

    public PaymentResponse createPayment(UUID cardId, String type, BigDecimal amount){

        PaymentRequest request = new PaymentRequest(cardId, type, amount);

        return paymentRestClient.post().uri("/api/payments").body(request)
                .retrieve().body(PaymentResponse.class);
    }

    private record PaymentRequest(UUID cardId, String type, BigDecimal amount){
    }
}