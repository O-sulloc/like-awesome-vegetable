package com.i5e2.likeawesomevegetable.payment.deposit;

import com.i5e2.likeawesomevegetable.board.post.dto.PostPointActivateEnum;
import com.i5e2.likeawesomevegetable.payment.deposit.dto.DepositPendingRequest;
import com.i5e2.likeawesomevegetable.payment.deposit.dto.DepositPendingResponse;
import com.i5e2.likeawesomevegetable.payment.deposit.dto.DepositTransferResponse;
import com.i5e2.likeawesomevegetable.payment.point.UserPoint;

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

    public static DepositPendingResponse from(UserPointDeposit userPointDeposit, PostPointActivateEnum updatePostActivate) {
        return DepositPendingResponse.builder()
                .userPointDepositId(userPointDeposit.getId())
                .pointTotalBalance(userPointDeposit.getUserPoint().getPointTotalBalance())
                .depositTotalBalance(userPointDeposit.getUserPoint().getDepositTotalBalance())
                .depositTargetPostId(userPointDeposit.getDepositTargetPostId())
                .depositStatus(userPointDeposit.getDepositStatus())
                .depositCommission(calculatorCommission(userPointDeposit.getDepositAmount()))
                .depositType(userPointDeposit.getDepositType())
                .depositPendingAt(userPointDeposit.getDepositPendingAt())
                .targetPostActivateStatus(updatePostActivate)
                .build();
    }

    public static DepositTransferResponse createTransferResponse(UserPointDeposit userPointDeposit) {
        return DepositTransferResponse.builder()
                .userPointDepositId(userPointDeposit.getId())
                .pointUserId(userPointDeposit.getUserPoint().getId())
                .depositAmount(userPointDeposit.getDepositAmount())
                .depositTargetPostId(userPointDeposit.getDepositTargetPostId())
                .depositStatus(userPointDeposit.getDepositStatus().toString())
                .depositCommission(userPointDeposit.getDepositCommission())
                .depositType(userPointDeposit.getDepositType())
                .depositPendingAt(userPointDeposit.getDepositPendingAt())
                .depositTransferAt(userPointDeposit.getDepositTransferAt())
                .build();
    }

    private static Long calculatorCommission(Long depositAmount) {
        return Long.valueOf((long) (depositAmount * 0.18));
    }
}
