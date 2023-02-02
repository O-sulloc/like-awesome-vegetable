package com.i5e2.likeawesomevegetable.domain.payment.api;

import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentCardResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.entity.Payment;
import com.i5e2.likeawesomevegetable.domain.payment.api.entity.UserPaymentOrder;
import com.i5e2.likeawesomevegetable.domain.point.PointFactory;
import com.i5e2.likeawesomevegetable.domain.point.dto.PointEventDetailResponse;
import com.i5e2.likeawesomevegetable.domain.point.entity.PointEventLog;
import com.i5e2.likeawesomevegetable.repository.PaymentJpaRepository;
import com.i5e2.likeawesomevegetable.repository.PointEventLogJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserPaymentOrderJpaRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Getter
@Service
@RequiredArgsConstructor
public class PointManagerService {
    private final UserPaymentOrderJpaRepository userPaymentOrderJpaRepository;
    private final PaymentJpaRepository paymentJpaRepository;
    private final PointEventLogJpaRepository pointEventLogJpaRepository;

    @Transactional(timeout = 2, rollbackFor = Exception.class)
    public PointEventDetailResponse savePaymentAndPoint(PaymentCardResponse paymentCardResponse) {
        Optional<UserPaymentOrder> userByPostOrderId = userPaymentOrderJpaRepository.findByPostOrderId(paymentCardResponse.getOrderId());
        Payment payment = PaymentFactory.createPayment(paymentCardResponse, userByPostOrderId.get());
        paymentJpaRepository.save(payment);

        PointEventLog pointEventLog = PointFactory.createPointEventLog(payment);
        PointEventLog pointDetailResult = pointEventLogJpaRepository.save(pointEventLog);
        return PointFactory.from(pointDetailResult);
    }

}
