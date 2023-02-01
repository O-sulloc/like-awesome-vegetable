package com.i5e2.likeawesomevegetable.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentConfirmController {

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String paymentSuccess(@RequestParam("paymentKey") String paymentKey
            , @RequestParam("orderId") String orderId
            , @RequestParam("amount") Long amount) {
        log.info("paymentKey: {}, orderId:{}, amount:{} ", paymentKey, orderId, amount);
        return "";
    }

}
