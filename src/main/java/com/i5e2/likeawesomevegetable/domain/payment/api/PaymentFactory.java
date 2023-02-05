package com.i5e2.likeawesomevegetable.domain.payment.api;

import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentCardResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.entity.Payment;
import com.i5e2.likeawesomevegetable.domain.payment.api.entity.UserPaymentOrder;

public class PaymentFactory {
    public static Payment createPayment(PaymentCardResponse paymentCardResponse, UserPaymentOrder userPaymentOrder) {
        return Payment.addUserPaymentOrder()
                .id(paymentCardResponse.getPaymentKey())
                .userPaymentOrder(userPaymentOrder)
                .paymentAmount(paymentCardResponse.getTotalAmount())
                .paymentMethod(paymentCardResponse.getMethod())
                .paymentStatus(paymentCardResponse.getStatus())
                .paymentType(paymentCardResponse.getType())
                .paymentRequestedAt(paymentCardResponse.getRequestedAt())
                .paymentApprovedAt(paymentCardResponse.getApprovedAt())
                .paymentOrderName(paymentCardResponse.getOrderName())
                .build();
    }
}
