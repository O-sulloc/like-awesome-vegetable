package com.i5e2.likeawesomevegetable.payment.api.dto;

import com.i5e2.likeawesomevegetable.payment.point.dto.PointEventDetailResponse;
import com.i5e2.likeawesomevegetable.payment.point.dto.UserPointResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentToCancelResponse {
    private PaymentRefundResponse paymentRefundResponse;
    private PointEventDetailResponse pointCancelDetailResponse;
    private UserPointResponse userPointResponse;
}
