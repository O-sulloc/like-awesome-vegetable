package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.payment.api.PaymentApiService;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.*;
import com.i5e2.likeawesomevegetable.domain.point.UserPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentApiController {
    private final PaymentApiService paymentApiService;
    private final UserPointService userPointService;

    @PostMapping("/point-info")
    public ResponseEntity<Result> checkMyPoint(@RequestBody @Valid PaymentInfoRequest paymentInfoRequest, Authentication authentication) {
        log.info("PaymentInfoRequest:{}", paymentInfoRequest.toString());
        userPointService.checkUserPointInfo(authentication.getName());
        UserPaymentOrderResponse userPaymentOrderResponse = paymentApiService.addUserPaymentToOrder(paymentInfoRequest, authentication.getName());
        PaymentOrderPointResponse paymentOrderPointResponse = userPointService.comparePointDeposit(paymentInfoRequest, authentication.getName());
        return ResponseEntity
                .ok()
                .body(Result.success(new PaymentApiOrderResponse(userPaymentOrderResponse, paymentOrderPointResponse)));
    }

    @PostMapping("/cancel-info")
    public ResponseEntity<Result> checkMyPoint(@RequestBody @Valid CancelInfoRequest cancelInfoRequest, Authentication authentication) {
        UserCancelOrderResponse userCancelOrderResponse = paymentApiService.cancelUserPaymentToOrder(cancelInfoRequest, authentication.getName());
        return ResponseEntity.ok().body(Result.success(userCancelOrderResponse));
    }
}
