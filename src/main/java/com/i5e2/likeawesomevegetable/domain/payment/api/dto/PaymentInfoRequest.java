package com.i5e2.likeawesomevegetable.domain.payment.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoRequest {
    /*userId, post_order_id, payment_order_post, payment_order_amount
        point_total_balance, 모집금액 혹은 입찰가*/
    private Long userId;
    private String postTitle;
    private String postOrderId;
    private Long paymentOrderPostId;
    private Long paymentOrderAmount;
    private Long pointTotalBalance;
    private Long requestDepositAmount;
}
