package com.i5e2.likeawesomevegetable.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminPaymentOrderResponse {
    private Long adminPaymentOrderId;
    private Long adminId;
    private String adminName;
    private String adminOrderId;
    private String adminOrderInfo;
    private Long adminTransferAmount;
}
