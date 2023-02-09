package com.i5e2.likeawesomevegetable.domain.payment.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApiOrderResponse {
    private UserPaymentOrderResponse userPaymentOrderResponse;
    private PaymentOrderPointResponse paymentOrderPointResponse;
}
