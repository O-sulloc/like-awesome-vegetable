package com.i5e2.likeawesomevegetable.domain.payment.api;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.payment.UserPaymentOrderFactory;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserPaymentOrderRequest;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.UserPaymentOrderResponse;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserPaymentOrderJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentApiService {

    private final UserJpaRepository userJpaRepository;
    private final UserPaymentOrderJpaRepository userPaymentOrderJpaRepository;

    public Result<UserPaymentOrderResponse> addUserPaymentToOrder(UserPaymentOrderRequest userPaymentOrderRequest) {
        User getUser = getUserOne(userPaymentOrderRequest.getUserId());

        //데이터 저장
        UserPaymentOrder userPaymentOrder = UserPaymentOrderFactory
                .createUserPaymentOrder(getUser, userPaymentOrderRequest);
        log.info("userPaymentOrder: {}", userPaymentOrder);
        userPaymentOrderJpaRepository.save(userPaymentOrder);

        //DTO 변환
        return Result.success(UserPaymentOrderFactory.of(userPaymentOrder));
    }

    private User getUserOne(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException());
    }


}
