package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.payment.api.PaymentApiService;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserPaymentOrderRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserPaymentOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentApiController {

    private final PaymentApiService paymentApiService;

    @GetMapping("/company")
    public String checkMyPoint() {
        return "market/company-gather-write-payment";
    }

    @PostMapping("/order")
    public ResponseEntity<Result<UserPaymentOrderResponse>> userPaymentToOrder(@RequestBody UserPaymentOrderRequest userPaymentOrderRequest) {
        Result<UserPaymentOrderResponse> paymentOrderResponse = paymentApiService.addUserPaymentToOrder(userPaymentOrderRequest);
        return ResponseEntity.ok().body(paymentOrderResponse);
    }

}
