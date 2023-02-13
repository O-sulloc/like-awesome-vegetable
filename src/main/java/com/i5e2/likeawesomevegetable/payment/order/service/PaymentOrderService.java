package com.i5e2.likeawesomevegetable.payment.order.service;

import com.i5e2.likeawesomevegetable.common.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.common.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.payment.api.dto.CancelInfoRequest;
import com.i5e2.likeawesomevegetable.payment.api.dto.PaymentInfoRequest;
import com.i5e2.likeawesomevegetable.payment.order.UserPaymentOrder;
import com.i5e2.likeawesomevegetable.payment.order.UserPaymentOrderFactory;
import com.i5e2.likeawesomevegetable.payment.order.dto.UserCancelOrderResponse;
import com.i5e2.likeawesomevegetable.payment.order.dto.UserPaymentOrderResponse;
import com.i5e2.likeawesomevegetable.payment.order.repository.UserPaymentOrderJpaRepository;
import com.i5e2.likeawesomevegetable.payment.point.UserPoint;
import com.i5e2.likeawesomevegetable.payment.point.repository.UserPointJpaRepository;
import com.i5e2.likeawesomevegetable.user.basic.User;
import com.i5e2.likeawesomevegetable.user.basic.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentOrderService {
    private final UserJpaRepository userJpaRepository;
    private final UserPointJpaRepository userPointJpaRepository;
    private final UserPaymentOrderJpaRepository userPaymentOrderJpaRepository;

    public UserPaymentOrderResponse addUserPaymentToOrder(PaymentInfoRequest paymentInfoRequest, String userEmail) {
        User getUser = getUserOne(userEmail);
        UserPoint userPoint = userPointJpaRepository.findByUserId(getUser.getId())
                .orElseThrow(() -> {
                    throw new AwesomeVegeAppException(AppErrorCode.NO_POINT_RESULT,
                            AppErrorCode.NO_POINT_RESULT.getMessage());
                });

        UserPaymentOrder userPaymentOrder = UserPaymentOrderFactory.createUserPaymentOrder(getUser, paymentInfoRequest, userPoint);
        userPaymentOrderJpaRepository.save(userPaymentOrder);

        return UserPaymentOrderFactory.of(userPaymentOrder);
    }

    public UserCancelOrderResponse cancelUserPaymentToOrder(CancelInfoRequest cancelInfoRequest, String userEmail) {
        User getUser = getUserOne(userEmail);
        UserPoint userPoint = userPointJpaRepository.findByUserId(getUser.getId())
                .orElseThrow(() -> {
                    throw new AwesomeVegeAppException(AppErrorCode.NO_POINT_RESULT,
                            AppErrorCode.NO_POINT_RESULT.getMessage());
                });

        if (cancelInfoRequest.getCancelOrderAmount() > userPoint.getPointTotalBalance()) {
            throw new AwesomeVegeAppException(AppErrorCode.REFUND_AMOUNT_ERROR,
                    AppErrorCode.REFUND_AMOUNT_ERROR.getMessage());
        }

        UserPaymentOrder userCancelOrder = UserPaymentOrderFactory.createUserCancelOrder(cancelInfoRequest, getUser);
        userPaymentOrderJpaRepository.save(userCancelOrder);

        return UserPaymentOrderFactory.from(userCancelOrder);
    }

    private User getUserOne(String userEmail) {
        return userJpaRepository.findByEmail(userEmail)
                .orElseThrow(() -> {
                    throw new AwesomeVegeAppException(AppErrorCode.EMAIL_NOT_FOUND,
                            AppErrorCode.EMAIL_NOT_FOUND.getMessage());
                });
    }
}
