package com.i5e2.likeawesomevegetable.payment.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @GetMapping("")
    public String userPaymentRequest() {
        return "point/point-check-payment";
    }
}
