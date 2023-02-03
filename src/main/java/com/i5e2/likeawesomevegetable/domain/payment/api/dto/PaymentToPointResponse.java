package com.i5e2.likeawesomevegetable.domain.payment.api.dto;

import com.i5e2.likeawesomevegetable.domain.point.dto.PointEventDetailResponse;
import com.i5e2.likeawesomevegetable.domain.point.dto.UserPointResponse;
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
