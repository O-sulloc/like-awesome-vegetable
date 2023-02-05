package com.i5e2.likeawesomevegetable.domain.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepositPendingRequest {
    private Long userPointId;
    private Long depositAmount;
    private Long depositTargetPostId;
    private String depositType;
}
