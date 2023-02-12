package com.i5e2.likeawesomevegetable.domain.payment.api;

import com.i5e2.likeawesomevegetable.domain.payment.api.dto.CancelInfoRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentInfoRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserCancelOrderResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserPaymentOrderResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.entity.UserPaymentOrder;
import com.i5e2.likeawesomevegetable.domain.point.entity.UserPoint;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.user.UserType;
import com.i5e2.likeawesomevegetable.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.exception.AwesomeVegeAppException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
public class UserPaymentOrderFactory {
    public static UserPaymentOrder createUserPaymentOrder(User getUser, PaymentInfoRequest paymentInfoRequest, UserPoint userPoint) {
        return UserPaymentOrder.builder()
                .user(getUser)
                .postOrderId(makePostOrderNumber(getUser.getUserType().name()))
                .paymentOrderPost(paymentInfoRequest.getPostTitle())
                .paymentOrderAmount(calculationAmount(paymentInfoRequest.getPaymentOrderAmount(), userPoint))
                .build();
    }

    public static UserPaymentOrderResponse of(UserPaymentOrder userPaymentOrder) {
        return new UserPaymentOrderResponse(
                userPaymentOrder.getPaymentOrderAmount()
                , userPaymentOrder.getPostOrderId()
                , userPaymentOrder.getPaymentOrderPost()
                , userPaymentOrder.getUser().getManagerName()
        );
    }

    public static UserCancelOrderResponse from(UserPaymentOrder userPaymentOrder) {
        return new UserCancelOrderResponse(
                userPaymentOrder.getUser().getId()
                , userPaymentOrder.getUser().getManagerName()
                , userPaymentOrder.getPostOrderId()
                , userPaymentOrder.getPaymentOrderPost()
                , userPaymentOrder.getPaymentOrderAmount()
        );
    }

    public static UserPaymentOrder createUserCancelOrder(CancelInfoRequest cancelInfoRequest, User getUser) {
        return UserPaymentOrder.builder()
                .user(getUser)
                .postOrderId(makeCancelOrderNumber(getUser.getUserType().name()))
                .paymentOrderPost("결제 포인트 환불 요청")
                .paymentOrderAmount(cancelInfoRequest.getCancelOrderAmount())
                .build();
    }

    private static String makePostOrderNumber(String userType) {
        if (userType.equals(userType.equals(UserType.ROLE_BASIC)) || UserType.values() == null) {
            throw new AwesomeVegeAppException(AppErrorCode.INVALID_PERMISSION,
                    AppErrorCode.INVALID_PERMISSION.getMessage());
        }

        String postOrderId = "";
        if (userType.equals(UserType.ROLE_COMPANY.toString())) {
            postOrderId = "POST-C-ORDER-" + LocalDate.now() + "-" + UUID.randomUUID();
        }

        if (userType.equals(UserType.ROLE_FARM.toString())) {
            postOrderId = "POST-F-ORDER-" + LocalDate.now() + "-" + UUID.randomUUID();
        }

        log.info("postOrderId: {}", postOrderId);
        return postOrderId;
    }

    private static String makeCancelOrderNumber(String userType) {
        return "CANCEL-" + userType + "-" + LocalDate.now() + "-" + UUID.randomUUID();
    }

    private static Long calculationAmount(Long paymentOrderAmount, UserPoint userPoint) {
        try {
            if (userPoint.getPointTotalBalance() == 0 || userPoint.getPointTotalBalance() == null) {
                return paymentOrderAmount;
            }
        } catch (RuntimeException e) {
            throw new AwesomeVegeAppException(AppErrorCode.NO_POINT_RESULT,
                    AppErrorCode.NO_POINT_RESULT.getMessage());
        }
        return paymentOrderAmount - (userPoint.getPointTotalBalance() - userPoint.getDepositTotalBalance());
    }

}
