package com.i5e2.likeawesomevegetable.domain.payment.api;

import com.amazonaws.services.kms.model.NotFoundException;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.CancelInfoRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentInfoRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserCancelOrderResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserPaymentOrderResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.entity.UserPaymentOrder;
import com.i5e2.likeawesomevegetable.domain.point.entity.UserPoint;
import com.i5e2.likeawesomevegetable.domain.user.User;
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

    public UserPaymentOrderResponse addUserPaymentToOrder(PaymentInfoRequest paymentInfoRequest) {
        User getUser = getUserOne(paymentInfoRequest.getUserId());

        UserPoint userPoint = userPointJpaRepository.findByUser(getUser)
                .orElseThrow(() -> new NotFoundException("사용자 포인트 정보가 존재하지 않습니다."));
        UserPaymentOrder userPaymentOrder = UserPaymentOrderFactory.createUserPaymentOrder(getUser, paymentInfoRequest, userPoint);
        userPaymentOrderJpaRepository.save(userPaymentOrder);

        return UserPaymentOrderFactory.of(userPaymentOrder);
    }

    public UserCancelOrderResponse cancelUserPaymentToOrder(CancelInfoRequest cancelInfoRequest) {
        User getUser = getUserOne(cancelInfoRequest.getCancelUserId());
        UserPaymentOrder userCancelOrder = UserPaymentOrderFactory.createUserCancelOrder(cancelInfoRequest, getUser);
        userPaymentOrderJpaRepository.save(userCancelOrder);

        return UserPaymentOrderFactory.from(userCancelOrder);
    }

    private User getUserOne(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 유저가 존재하지 않습니다.")); //TODO: 예외처리
    }
}
