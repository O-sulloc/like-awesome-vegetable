package com.i5e2.likeawesomevegetable.domain.point;

import com.i5e2.likeawesomevegetable.domain.admin.dto.TransferEventDetailResponse;
import com.i5e2.likeawesomevegetable.domain.deposit.dto.DepositAvailableStatus;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentInfoRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentOrderPointResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.entity.Payment;
import com.i5e2.likeawesomevegetable.domain.point.dto.PointEventDetailResponse;
import com.i5e2.likeawesomevegetable.domain.point.dto.UserPointResponse;
import com.i5e2.likeawesomevegetable.domain.point.entity.PointEventLog;
import com.i5e2.likeawesomevegetable.domain.point.entity.UserPoint;
import com.i5e2.likeawesomevegetable.domain.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PointFactory {
    public static PointEventLog createPointEventLog(Payment payment) {
        return PointEventLog.builder()
                .payment(payment)
                .pointEventStatus(payment.getPaymentType())
                .pointEventHistory(payment.getPaymentOrderName())
                .pointEventAmount(payment.getPaymentAmount())
                .pointRequestAt(payment.getPaymentRequestedAt())
                .pointApprovedAt(payment.getPaymentApprovedAt())
                .pointUserId(payment.getUserPaymentOrder().getUser().getId())
                .build();
    }

    public static PointEventLog createTransferEventLog(Payment payment) {
        return PointEventLog.builder()
                .payment(payment)
                .pointEventStatus("ADMIN-TRANSFER")
                .pointEventHistory(payment.getPaymentOrderName())
                .pointEventAmount(payment.getPaymentAmount())
                .pointRequestAt(payment.getPaymentRequestedAt())
                .pointApprovedAt(payment.getPaymentApprovedAt())
                .pointUserId(payment.getAdminPaymentOrder().getAdminUser().getId())
                .build();
    }

    public static PointEventDetailResponse of(PointEventLog pointEventLog) {
        return PointEventDetailResponse.builder()
                .pointEventLogId(pointEventLog.getId())
                .pointTargetUserId(pointEventLog.getPointTargetUserId())
                .pointDetailHistory(pointEventLog.getPointEventHistory())
                .pointDetailStatus(pointEventLog.getPayment().getPaymentStatus())
                .pointUserId(pointEventLog.getPointUserId())
                .pointEventAmount(pointEventLog.getPointEventAmount())
                .pointUsedEventAt(pointEventLog.getPointUsedEventAt())
                .build();
    }

    public static TransferEventDetailResponse form(PointEventLog transferEventLog) {
        return TransferEventDetailResponse.builder()
                .transferEventLogId(transferEventLog.getId())
                .transferTargetUserId(transferEventLog.getPointTargetUserId())
                .transferDetailHistory(transferEventLog.getPointEventHistory())
                .transferDetailStatus(transferEventLog.getPayment().getPaymentStatus())
                .transferUserId(transferEventLog.getPointUserId())
                .transferEventAmount(transferEventLog.getPointEventAmount())
                .transferUsedEventAt(transferEventLog.getPointUsedEventAt())
                .build();
    }

    public static UserPoint of(User user) {
        return new UserPoint(user, 0L, 0L);
    }

    public static UserPointResponse from(UserPoint userPoint) {
        return UserPointResponse.builder()
                .userPointId(userPoint.getId())
                .userTotalBalance(userPoint.getPointTotalBalance())
                .userDepositBalance(userPoint.getDepositTotalBalance())
                .userId(userPoint.getUser().getId())
                .managerName(userPoint.getUser().getManagerName())
                .userType(userPoint.getUser().getUserType())
                .build();
    }

    public static PaymentOrderPointResponse of(PaymentInfoRequest paymentInfoRequest, UserPoint userPointDeposit) {
        PaymentOrderPointResponse paymentOrderPointResponse = new PaymentOrderPointResponse(paymentInfoRequest.getPointTotalBalance()
                , makePaymentAmount(paymentInfoRequest.getPointTotalBalance(), paymentInfoRequest.getPaymentOrderAmount())
                , comparePointDeposit(paymentInfoRequest, userPointDeposit.getPointTotalBalance()));
        log.info("paymentOrderPointResponse:{}", paymentOrderPointResponse);
        return paymentOrderPointResponse;
    }

    private static Long makePaymentAmount(Long pointTotalBalance, Long paymentOrderAmount) {
        return (paymentOrderAmount - pointTotalBalance);
    }

    private static DepositAvailableStatus comparePointDeposit(PaymentInfoRequest paymentInfoRequest, Long userPointTotalBalance) {
        return (userPointTotalBalance >= paymentInfoRequest.getPaymentOrderAmount())
                ? DepositAvailableStatus.DEPOSIT_AVAILABLE
                : DepositAvailableStatus.DEPOSIT_NOT_AVAILABLE;
    }
}
