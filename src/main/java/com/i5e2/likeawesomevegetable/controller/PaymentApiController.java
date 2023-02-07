package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.payment.api.PaymentApiService;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.*;
import com.i5e2.likeawesomevegetable.domain.point.UserPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentApiController {
    private final PaymentApiService paymentApiService;
    private final UserPointService userPointService;

    @PostMapping("/point-info")
    public String checkMyPoint(@ModelAttribute PaymentInfoRequest paymentInfoRequest, Model model) {
        userPointService.checkUserPointInfo(paymentInfoRequest.getUserId());
        UserPaymentOrderResponse userPaymentOrderResponse = paymentApiService.addUserPaymentToOrder(paymentInfoRequest);
        PaymentOrderPointResponse paymentOrderPointResponse = userPointService.comparePointDeposit(paymentInfoRequest);
        model.addAttribute("userPaymentOrderResponse", userPaymentOrderResponse);
        model.addAttribute("paymentOrderPointResponse", paymentOrderPointResponse);
        return "point/point-check-payment";
    }

    @PostMapping("/cancel-info")
    public ResponseEntity<Result<UserCancelOrderResponse>> checkMyPoint(@RequestBody CancelInfoRequest cancelInfoRequest) {
        UserCancelOrderResponse userCancelOrderResponse = paymentApiService.cancelUserPaymentToOrder(cancelInfoRequest);
        return ResponseEntity.ok().body(Result.success(userCancelOrderResponse));
    }
}
