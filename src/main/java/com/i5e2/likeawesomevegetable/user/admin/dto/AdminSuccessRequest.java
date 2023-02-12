package com.i5e2.likeawesomevegetable.user.admin.dto;

import lombok.Data;

@Data
public class AdminSuccessRequest {
    private String paymentKey;
    private String orderId;
    private Long amount;
    private Long targetUserId;
    private Long depositAmount;
}
