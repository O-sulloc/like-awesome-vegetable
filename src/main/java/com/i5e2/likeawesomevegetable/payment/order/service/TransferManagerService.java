package com.i5e2.likeawesomevegetable.payment.order.service;

import com.i5e2.likeawesomevegetable.common.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.common.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.payment.api.Payment;
import com.i5e2.likeawesomevegetable.payment.api.PaymentFactory;
import com.i5e2.likeawesomevegetable.payment.api.dto.AdminTransferResponse;
import com.i5e2.likeawesomevegetable.payment.api.dto.TransferEventDetailResponse;
import com.i5e2.likeawesomevegetable.payment.api.repository.PaymentJpaRepository;
import com.i5e2.likeawesomevegetable.payment.order.AdminPaymentOrder;
import com.i5e2.likeawesomevegetable.payment.order.repository.AdminPaymentOrderJpaRepository;
import com.i5e2.likeawesomevegetable.payment.point.PointEventLog;
import com.i5e2.likeawesomevegetable.payment.point.PointFactory;
import com.i5e2.likeawesomevegetable.payment.point.repository.PointEventLogJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferManagerService {
    private final PaymentJpaRepository paymentJpaRepository;
    private final AdminPaymentOrderJpaRepository adminPaymentOrderJpaRepository;
    private final PointEventLogJpaRepository pointEventLogJpaRepository;

    @Transactional(timeout = 2, rollbackFor = Exception.class)
    public TransferEventDetailResponse savePaymentAndTransfer(AdminTransferResponse adminTransferResponse, String orderId) {
        AdminPaymentOrder adminPaymentOrder = adminPaymentOrderJpaRepository.findByAdminOrderId(orderId)
                .orElseThrow(() -> {
                    throw new AwesomeVegeAppException(AppErrorCode.NO_PAYMENT_ORDER_RESULT,
                            AppErrorCode.NO_PAYMENT_ORDER_RESULT.getMessage());
                });

        Payment transfer = PaymentFactory.createTransfer(adminTransferResponse, adminPaymentOrder);
        paymentJpaRepository.save(transfer);

        PointEventLog transferEventLog = PointFactory.createTransferEventLog(transfer);
        PointEventLog transferDetailResult = pointEventLogJpaRepository.save(transferEventLog);
        return PointFactory.form(transferDetailResult, adminPaymentOrder);
    }
}
