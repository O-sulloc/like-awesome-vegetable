package com.i5e2.likeawesomevegetable.domain.payment.api.dto;

import com.i5e2.likeawesomevegetable.domain.point.dto.DepositAvailableStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderPointResponse {
    private Long totalPointBalanceByUser;
    private Long chargePointPayment;
    private DepositAvailableStatus depositAvailableStatus;
}
