package com.i5e2.likeawesomevegetable.domain.payment.api;

import com.i5e2.likeawesomevegetable.domain.payment.api.dto.CancelInfoRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentInfoRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserCancelOrderResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserPaymentOrderResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.entity.UserPaymentOrder;
import com.i5e2.likeawesomevegetable.domain.point.entity.UserPoint;
import com.i5e2.likeawesomevegetable.domain.point.exception.PointErrorCode;
import com.i5e2.likeawesomevegetable.domain.point.exception.PointException;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.user.UserErrorCode;
import com.i5e2.likeawesomevegetable.domain.user.UserException;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserPaymentOrderJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserPointJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentApiService {
    private final UserJpaRepository userJpaRepository;
    private final UserPointJpaRepository userPointJpaRepository;
    private final UserPaymentOrderJpaRepository userPaymentOrderJpaRepository;

    public UserPaymentOrderResponse addUserPaymentToOrder(PaymentInfoRequest paymentInfoRequest, String userEmail) {
        User getUser = getUserOne(userEmail);

        UserPoint userPoint = userPointJpaRepository.findByUser(getUser)
                .orElseThrow(() -> {
                    throw new PointException(PointErrorCode.NO_POINT_RESULT,
                            PointErrorCode.NO_POINT_RESULT.getMessage());
                });
        UserPaymentOrder userPaymentOrder = UserPaymentOrderFactory.createUserPaymentOrder(getUser, paymentInfoRequest, userPoint);
        userPaymentOrderJpaRepository.save(userPaymentOrder);

        return UserPaymentOrderFactory.of(userPaymentOrder);
    }

    public UserCancelOrderResponse cancelUserPaymentToOrder(CancelInfoRequest cancelInfoRequest, String userEmail) {
        User getUser = getUserOne(userEmail);
        UserPaymentOrder userCancelOrder = UserPaymentOrderFactory.createUserCancelOrder(cancelInfoRequest, getUser);
        userPaymentOrderJpaRepository.save(userCancelOrder);

        return UserPaymentOrderFactory.from(userCancelOrder);
    }

    private User getUserOne(String userEmail) {
        return userJpaRepository.findByEmail(userEmail)
                .orElseThrow(() -> {
                    throw new UserException(UserErrorCode.EMAIL_NOT_FOUND,
                            UserErrorCode.EMAIL_NOT_FOUND.getMessage());
                });
    }
}
