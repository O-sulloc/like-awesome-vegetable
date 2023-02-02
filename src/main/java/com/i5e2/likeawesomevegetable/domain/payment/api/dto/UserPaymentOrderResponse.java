package com.i5e2.likeawesomevegetable.domain.payment.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentOrderResponse {
    private Long paymentOrderAmount;
    private String orderName;
    private String postOrderId;
    private String paymentOrderTitle;
}
