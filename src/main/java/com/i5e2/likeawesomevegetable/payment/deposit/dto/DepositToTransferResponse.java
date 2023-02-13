package com.i5e2.likeawesomevegetable.payment.deposit.dto;

import com.i5e2.likeawesomevegetable.payment.api.dto.AdminTransferResponse;
import com.i5e2.likeawesomevegetable.payment.api.dto.TransferEventDetailResponse;
import com.i5e2.likeawesomevegetable.payment.point.dto.UserPointResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepositToTransferResponse {
    private AdminTransferResponse adminTransferResponse;
    private TransferEventDetailResponse transferEventDetailResponse;
    private UserPointResponse userPointResponse;
    private DepositTransferResponse depositTransferResponse;
}
