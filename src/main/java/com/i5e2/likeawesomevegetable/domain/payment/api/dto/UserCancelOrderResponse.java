package com.i5e2.likeawesomevegetable.domain.payment.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCancelOrderResponse {
    private Long userId;
    private String userName;
    private String cancelOrderId;
    private String cancelOrderComment;
    private Long cancelOrderAmount;
}
