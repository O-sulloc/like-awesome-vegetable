package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Response;
import com.i5e2.likeawesomevegetable.domain.payment.api.PaymentApiService;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserPaymentOrderRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserPaymentOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/payment")
public class PaymentApiController {

    private final PaymentApiService paymentApiService;

    @GetMapping("/company")
    public String checkMyPoint() {
        return "market/company-gather-write-payment";
    }

    @PostMapping("/order")
    public ResponseEntity<Response> userPaymentToOrder(@RequestBody UserPaymentOrderRequest userPaymentOrderRequest) {
        Response<UserPaymentOrderResponse> paymentOrderResponse = paymentApiService.addUserPaymentToOrder(userPaymentOrderRequest);
        return ResponseEntity.ok().body(paymentOrderResponse);
    }

}
