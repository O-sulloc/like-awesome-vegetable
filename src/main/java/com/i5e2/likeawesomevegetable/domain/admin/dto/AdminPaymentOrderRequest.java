package com.i5e2.likeawesomevegetable.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminPaymentOrderRequest {
    private Long adminId;
    private String adminOrderInfo;
    private Long transferTotalAmount;
}
