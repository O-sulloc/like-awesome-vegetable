package com.i5e2.likeawesomevegetable.domain.admin.dto;

import com.i5e2.likeawesomevegetable.domain.point.dto.UserPointResponse;
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
