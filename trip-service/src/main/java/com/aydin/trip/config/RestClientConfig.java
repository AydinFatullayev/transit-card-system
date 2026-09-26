package com.aydin.trip.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient walletRestClient(
            @Value("${wallet-service.url}") String walletServiceUrl
    ) {

        return RestClient.builder()
                .baseUrl(walletServiceUrl)
                .build();
    }
}