package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.payment.api.PaymentApiService;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.*;
import com.i5e2.likeawesomevegetable.domain.point.UserPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentApiController {
    private final PaymentApiService paymentApiService;
    private final UserPointService userPointService;

    @PostMapping("/point-info")
    public ResponseEntity checkMyPoint(@RequestBody PaymentInfoRequest paymentInfoRequest) {
        userPointService.checkUserPointInfo(paymentInfoRequest.getUserId());
        UserPaymentOrderResponse userPaymentOrderResponse = paymentApiService.addUserPaymentToOrder(paymentInfoRequest);
        PaymentOrderPointResponse paymentOrderPointResponse = userPointService.comparePointDeposit(paymentInfoRequest);
        return ResponseEntity
                .ok()
                .body(Result.success(new PaymentApiOrderResponse(userPaymentOrderResponse, paymentOrderPointResponse)));
    }

    @PostMapping("/cancel-info")
    public ResponseEntity<Result<UserCancelOrderResponse>> checkMyPoint(@RequestBody CancelInfoRequest cancelInfoRequest) {
        UserCancelOrderResponse userCancelOrderResponse = paymentApiService.cancelUserPaymentToOrder(cancelInfoRequest);
        return ResponseEntity.ok().body(Result.success(userCancelOrderResponse));
    }
}
