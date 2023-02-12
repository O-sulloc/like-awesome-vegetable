package com.i5e2.likeawesomevegetable.payment.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoRequest {
    private Long userId;
    private String postTitle;
    private Long paymentOrderPostId;
    private Long paymentOrderAmount;
    private Long pointTotalBalance;
    private Long requestDepositAmount;
}
