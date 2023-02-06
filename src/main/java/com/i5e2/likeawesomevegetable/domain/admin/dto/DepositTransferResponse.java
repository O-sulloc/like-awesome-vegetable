package com.i5e2.likeawesomevegetable.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositTransferResponse {
    private Long userPointDepositId;
    private Long pointUserId;
    private Long depositAmount;
    private Long depositTargetPostId;
    private String depositStatus;
    private Long depositCommission;
    private String depositType;
    private LocalDateTime depositPendingAt;
    private LocalDateTime depositTransferAt;
}
