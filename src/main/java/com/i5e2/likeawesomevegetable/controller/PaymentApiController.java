package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.payment.api.PaymentApiService;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentInfoRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentOrderPointResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserPaymentOrderResponse;
import com.i5e2.likeawesomevegetable.domain.point.UserPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        UserPaymentOrderResponse userPaymentOrderResponse = paymentApiService.addUserPaymentToOrder(paymentInfoRequest);
        PaymentOrderPointResponse paymentOrderPointResponse = userPointService.comparePointDeposit(paymentInfoRequest);
        log.info("paymentOrderPointResponse:{}", paymentOrderPointResponse);
        model.addAttribute("userPaymentOrderResponse", userPaymentOrderResponse);
        model.addAttribute("paymentOrderPointResponse", paymentOrderPointResponse);
        return "market/company-gather-write-payment";
    }
}
