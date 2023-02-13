package com.i5e2.likeawesomevegetable.payment.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentOrderResponse {
    private Long paymentOrderAmount;
    private String postOrderId;//생성한 order number
    private String orderName;//post title
    private String customerName; //manager name
}
