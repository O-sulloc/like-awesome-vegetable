package com.i5e2.likeawesomevegetable.domain.payment.api;

import com.amazonaws.services.kms.model.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentCardResponse;
import com.i5e2.likeawesomevegetable.repository.UserPaymentOrderJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentConfirmService {

    @Value("${toss.api.testSecretApiKey}")
    private String testSecretApiKey;

    private final UserPaymentOrderJpaRepository userPaymentOrderJpaRepository;
    private final ObjectMapper objectMapper;

    @Transactional(timeout = 2)
    public void verifySuccessRequest(String orderId, Long amount) {
        UserPaymentOrder userPaymentOrder = userPaymentOrderJpaRepository.findByPostOrderId(orderId)
                .orElseThrow(() -> new NotFoundException("사용자 요청 데이터가 존재하지 않습니다"));
        log.info("userRequestAmount: {}, paymentAmount: {}", userPaymentOrder.getPaymentOrderAmount(), amount);
    }

    @Transactional
    public Result<PaymentCardResponse> requestFinalPayment(String paymentKey, String orderId, Long amount) throws IOException, InterruptedException {
        testSecretApiKey = testSecretApiKey + ":";
        String authKey = new String(Base64.getEncoder().encode(testSecretApiKey.getBytes(StandardCharsets.UTF_8)));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/payments/confirm"))
                .header("Authorization", "Basic " + authKey)
                .header("Content-Type", "application/json")
                .method("POST"
                        , HttpRequest
                                .BodyPublishers
                                .ofString("{\"paymentKey\":\"" + paymentKey + "\",\"amount\":\"" + amount + "\",\"orderId\":\"" + orderId + "\"}")
                ).build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        PaymentCardResponse paymentCardResponse = objectMapper.readValue(response.body(), PaymentCardResponse.class);

        return Result.success(paymentCardResponse);
    }

}
