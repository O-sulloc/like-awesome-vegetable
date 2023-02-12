package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.payment.api.PaymentApiService;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.*;
import com.i5e2.likeawesomevegetable.domain.point.UserPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("Payment Order Request Controller")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentApiController {
    private final PaymentApiService paymentApiService;
    private final UserPointService userPointService;

    @ApiOperation(value = "사용자 결제 요청 (기업/농가)"
            , notes = "사용자 입력 데이트롤 통해 포인트 비교 후 가공된 결제 요청 데이터를 저장한다.")
    @PostMapping("/point-info")
    public ResponseEntity<Result> checkMyPoint(@RequestBody @Valid PaymentInfoRequest paymentInfoRequest, Authentication authentication) {
        UserPaymentOrderResponse userPaymentOrderResponse = paymentApiService.addUserPaymentToOrder(paymentInfoRequest, authentication.getName());
        PaymentOrderPointResponse paymentOrderPointResponse = userPointService.comparePointDeposit(paymentInfoRequest, authentication.getName());
        return ResponseEntity
                .ok()
                .body(Result.success(new PaymentApiOrderResponse(userPaymentOrderResponse, paymentOrderPointResponse)));
    }

    @ApiOperation(value = "사용자 결제 내역 전액 환불 요청 (기업/농가)"
            , notes = "사용자 결제시 입력받은 PaymentKey를 포함한 환불 요청 데이터를 저장한다.")
    @PostMapping("/refund-info")
    public ResponseEntity<Result> checkMyPoint(@RequestBody @Valid CancelInfoRequest cancelInfoRequest, Authentication authentication) {
        UserCancelOrderResponse userCancelOrderResponse = paymentApiService.cancelUserPaymentToOrder(cancelInfoRequest, authentication.getName());
        return ResponseEntity.ok().body(Result.success(userCancelOrderResponse));
    }
}
