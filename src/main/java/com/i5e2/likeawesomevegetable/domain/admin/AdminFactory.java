package com.i5e2.likeawesomevegetable.domain.admin;

import com.i5e2.likeawesomevegetable.domain.admin.dto.AdminPaymentOrderRequest;
import com.i5e2.likeawesomevegetable.domain.admin.dto.AdminPaymentOrderResponse;
import com.i5e2.likeawesomevegetable.domain.admin.entity.AdminPaymentOrder;
import com.i5e2.likeawesomevegetable.domain.admin.entity.AdminUser;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
public class AdminFactory {
    public static AdminPaymentOrder createAdminPaymentOrder(AdminUser adminUser, AdminPaymentOrderRequest adminPaymentOrderRequest) {
        return AdminPaymentOrder.builder()
                .adminUser(adminUser)
                .adminOrderId(makeAdminTransferOrderId())
                .adminOrderInfo(adminPaymentOrderRequest.getAdminOrderInfo())
                .adminTransferAmount(calculateTransferAmount(adminPaymentOrderRequest.getTransferTotalAmount()))
                .build();
    }

    public static AdminPaymentOrderResponse from(AdminPaymentOrder adminPaymentOrder) {
        return AdminPaymentOrderResponse.builder()
                .adminPaymentOrderId(adminPaymentOrder.getId())
                .adminId(adminPaymentOrder.getAdminUser().getId())
                .adminName(adminPaymentOrder.getAdminUser().getAdminName())
                .adminOrderId(adminPaymentOrder.getAdminOrderId())
                .adminOrderInfo(adminPaymentOrder.getAdminOrderInfo())
                .adminTransferAmount(adminPaymentOrder.getAdminTransferAmount())
                .build();
    }

    private static String makeAdminTransferOrderId() {
        return "ADMIN-TRANSFER-" + LocalDate.now() + "-" + UUID.randomUUID();
    }

    private static Long calculateTransferAmount(Long responseTotalAmount) {
        log.info("responseTotalAmount:{}", responseTotalAmount);
        double transferAmount = responseTotalAmount - (responseTotalAmount * 0.18);
        log.info("transferAmount:{}", transferAmount);
        return Long.valueOf((long) transferAmount);
    }
}
