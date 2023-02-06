package com.i5e2.likeawesomevegetable.domain.payment.api;

import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentInfoRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserPaymentOrderResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.entity.UserPaymentOrder;
import com.i5e2.likeawesomevegetable.domain.point.entity.UserPoint;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.user.UserType;
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

    private static String makePostOrderNumber(String userType) {
        String postOrderId;
        if (userType.equals(UserType.ROLE_COMPANY.toString())) {
            postOrderId = "POST-C-ORDER-" + LocalDate.now() + "-" + UUID.randomUUID();
        } else {
            postOrderId = "POST-F-ORDER-" + LocalDate.now() + "-" + UUID.randomUUID();
        }
        log.info("postOrderId: {}", postOrderId);
        return postOrderId;
    }

    public static UserPaymentOrderResponse of(UserPaymentOrder userPaymentOrder) {
        return new UserPaymentOrderResponse(
                userPaymentOrder.getPaymentOrderAmount()
                , userPaymentOrder.getPostOrderId()
                , userPaymentOrder.getPaymentOrderPost()
                , userPaymentOrder.getUser().getManagerName()
        );
    }

    private static Long calculationAmount(Long paymentOrderAmount, UserPoint userPoint) {
        return (paymentOrderAmount - (userPoint.getPointTotalBalance() - userPoint.getDepositTotalBalance()));
    }

}
