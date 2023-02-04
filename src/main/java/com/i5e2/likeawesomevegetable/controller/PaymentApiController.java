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
        //userId, post_order_id, payment_order_post, payment_order_amount
        //point_total_balance, 모집금액 혹은 입찰가
        return "market/company-gather-write-payment";
    }

    @PostMapping("/order")
    public ResponseEntity<Result<UserPaymentOrderResponse>> userPaymentToOrder(@RequestBody UserPaymentOrderRequest userPaymentOrderRequest) {
        Result<UserPaymentOrderResponse> paymentOrderResponse = paymentApiService.addUserPaymentToOrder(userPaymentOrderRequest);
        return ResponseEntity.ok().body(paymentOrderResponse);
    }
}
