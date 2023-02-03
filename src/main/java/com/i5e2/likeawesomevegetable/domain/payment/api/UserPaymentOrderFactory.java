package com.i5e2.likeawesomevegetable.domain.payment.api;

import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserPaymentOrderRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserPaymentOrderResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.entity.UserPaymentOrder;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.user.UserType;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
public class UserPaymentOrderFactory {
    public static UserPaymentOrder createUserPaymentOrder(User getUser, UserPaymentOrderRequest userPaymentOrderRequest) {
        return UserPaymentOrder.builder()
                .user(getUser)
                .postOrderId(makePostOrderNumber(getUser.getUserType().name()))
                .paymentOrderPost(userPaymentOrderRequest.getPostTitle())
                .paymentOrderAmount(userPaymentOrderRequest.getPaymentOrderAmount())
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
                , userPaymentOrder.getUser().getManagerName()
                , userPaymentOrder.getPostOrderId()
                , userPaymentOrder.getPaymentOrderPost()
        );
    }

}
