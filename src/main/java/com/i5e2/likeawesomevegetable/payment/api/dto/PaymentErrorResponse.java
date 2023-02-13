package com.i5e2.likeawesomevegetable.payment.api.dto;

import lombok.Data;

@Data
public class PaymentErrorResponse {
    private String code;
    private String message;
    private String orderId;
}
