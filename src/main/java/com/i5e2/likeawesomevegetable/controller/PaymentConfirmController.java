package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.payment.api.PaymentConfirmService;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentCardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentConfirmController {
    private final PaymentConfirmService paymentConfirmService;

    @RequestMapping(value = "/success")
    public ResponseEntity<Result<PaymentCardResponse>> paymentSuccess(@RequestParam("paymentKey") String paymentKey
            , @RequestParam("orderId") String orderId
            , @RequestParam("amount") Long amount) throws IOException, InterruptedException {

        paymentConfirmService.verifySuccessRequest(orderId, amount);
        Result<PaymentCardResponse> paymentResult = paymentConfirmService.requestFinalPayment(paymentKey, orderId, amount);
        return ResponseEntity.ok().body(paymentResult);
    }

}
