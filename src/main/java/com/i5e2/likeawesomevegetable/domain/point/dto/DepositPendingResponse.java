package com.i5e2.likeawesomevegetable.domain.point.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositPendingResponse {
    private Long userPointDepositId;
    private Long pointTotalBalance;
    private Long depositTotalBalance;
    private Long depositTargetPostId;
    private DepositStatus depositStatus;
    private Long depositCommission;
    private String depositType;
    private LocalDateTime depositPendingAt;
}
