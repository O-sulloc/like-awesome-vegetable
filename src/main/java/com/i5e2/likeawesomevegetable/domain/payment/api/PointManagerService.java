package com.i5e2.likeawesomevegetable.domain.payment.api;

import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentCardResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentRefundResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.entity.Payment;
import com.i5e2.likeawesomevegetable.domain.payment.api.entity.UserPaymentOrder;
import com.i5e2.likeawesomevegetable.domain.point.PointFactory;
import com.i5e2.likeawesomevegetable.domain.point.dto.PointEventDetailResponse;
import com.i5e2.likeawesomevegetable.domain.point.entity.PointEventLog;
import com.i5e2.likeawesomevegetable.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.exception.AwesomeVegeAppException;
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
        UserPaymentOrder userByPostOrderId = userPaymentOrderJpaRepository.findByPostOrderId(paymentCardResponse.getOrderId())
                .orElseThrow(() -> {
                    throw new AwesomeVegeAppException(AppErrorCode.NO_PAYMENT_ORDER_RESULT,
                            AppErrorCode.NO_PAYMENT_ORDER_RESULT.getMessage());
                });

        Payment payment = PaymentFactory.createPayment(paymentCardResponse, userByPostOrderId);
        paymentJpaRepository.save(payment);

        PointEventLog pointEventLog = PointFactory.createPointEventLog(payment);
        PointEventLog pointDetailResult = pointEventLogJpaRepository.save(pointEventLog);

        return PointFactory.of(pointDetailResult, userByPostOrderId.getUser().getEmail());
    }

    @Transactional(timeout = 2, rollbackFor = Exception.class)
    public PointEventDetailResponse cancelPaymentAndPoint(PaymentRefundResponse paymentRefundResponse) {
        Optional<UserPaymentOrder> userByCancelOrder = userPaymentOrderJpaRepository.findByPostOrderId(paymentRefundResponse.getOrderId());
        Payment cancel = PaymentFactory.createCancel(paymentRefundResponse, userByCancelOrder.get());
        paymentJpaRepository.save(cancel);

        PointEventLog pointEventLog = PointFactory.createCancelEventLog(cancel);
        PointEventLog cancelDetailResult = pointEventLogJpaRepository.save(pointEventLog);
        return PointFactory.of(cancelDetailResult, userByCancelOrder.get().getUser().getEmail());
    }

}
