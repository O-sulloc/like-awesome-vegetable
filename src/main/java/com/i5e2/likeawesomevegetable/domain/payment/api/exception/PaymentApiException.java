package com.i5e2.likeawesomevegetable.domain.payment.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApiException {
    private String code;
    private String message;
}
