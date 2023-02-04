package com.i5e2.likeawesomevegetable.domain.point;

import com.i5e2.likeawesomevegetable.domain.payment.api.entity.Payment;
import com.i5e2.likeawesomevegetable.domain.point.dto.PointEventDetailResponse;
import com.i5e2.likeawesomevegetable.domain.point.dto.UserPointResponse;
import com.i5e2.likeawesomevegetable.domain.point.entity.PointEventLog;
import com.i5e2.likeawesomevegetable.domain.point.entity.UserPoint;
import com.i5e2.likeawesomevegetable.domain.user.User;

public class PointFactory {
    public static PointEventLog createPointEventLog(Payment payment) {
        return PointEventLog.builder()
                .payment(payment)
                .paymentType(payment.getPaymentType())
                .pointEventHistory(payment.getPaymentOrderName())
                .pointEventAmount(payment.getPaymentAmount())
                .pointRequestAt(payment.getPaymentRequestedAt())
                .pointApprovedAt(payment.getPaymentApprovedAt())
                .pointUserId(payment.getUserPaymentOrder().getUser().getId())
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

    public static UserPoint of(User user, Long userTotalPoint) {
        return new UserPoint(user, userTotalPoint);
    }

    public static UserPointResponse from(UserPoint userPoint) {
        return UserPointResponse.builder()
                .userPointId(userPoint.getId())
                .userTotalBalance(userPoint.getPointTotalBalance())
                .userId(userPoint.getUser().getId())
                .managerName(userPoint.getUser().getManagerName())
                .userType(userPoint.getUser().getUserType())
                .build();
    }
}
