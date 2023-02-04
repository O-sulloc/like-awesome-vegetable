package com.i5e2.likeawesomevegetable.domain.point;

import com.i5e2.likeawesomevegetable.domain.point.dto.DepositPendingRequest;
import com.i5e2.likeawesomevegetable.domain.point.dto.DepositPendingResponse;
import com.i5e2.likeawesomevegetable.domain.point.entity.UserPoint;
import com.i5e2.likeawesomevegetable.domain.point.entity.UserPointDeposit;

public class DepositFactory {
    public static UserPointDeposit createPendingDeposit(DepositPendingRequest depositPendingRequest, UserPoint userPoint) {
        return UserPointDeposit.setPendingDeposit()
                .userPoint(userPoint)
                .depositAmount(depositPendingRequest.getDepositAmount())
                .depositTargetPostId(depositPendingRequest.getDepositTargetPostId())
                .depositCommission(calculatorCommission(depositPendingRequest.getDepositAmount()))
                .depositType(depositPendingRequest.getDepositType())
                .build();
    }

    public static DepositPendingResponse from(UserPointDeposit userPointDeposit) {
        return DepositPendingResponse.builder()
                .userPointDepositId(userPointDeposit.getId())
                .pointTotalBalance(userPointDeposit.getUserPoint().getPointTotalBalance())
                .depositTotalBalance(userPointDeposit.getUserPoint().getDepositTotalBalance())
                .depositTargetPostId(userPointDeposit.getDepositTargetPostId())
                .depositStatus(userPointDeposit.getDepositStatus())
                .depositCommission(calculatorCommission(userPointDeposit.getDepositAmount()))
                .depositType(userPointDeposit.getDepositType())
                .depositPendingAt(userPointDeposit.getDepositPendingAt())
                .build();
    }

    private static Long calculatorCommission(Long depositAmount) {
        return Long.valueOf((long) (depositAmount * 0.18));
    }
}
