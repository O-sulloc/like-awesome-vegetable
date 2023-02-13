package com.i5e2.likeawesomevegetable.payment.api.dto;

import com.i5e2.likeawesomevegetable.payment.point.dto.PointEventDetailResponse;
import com.i5e2.likeawesomevegetable.payment.point.dto.UserPointResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentToPointResponse {
    private PaymentCardResponse paymentCardResponse;
    private PointEventDetailResponse pointEventDetailResponse;
    private UserPointResponse userPointResponse;
}
