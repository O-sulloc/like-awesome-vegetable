package com.i5e2.likeawesomevegetable.domain.payment.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
