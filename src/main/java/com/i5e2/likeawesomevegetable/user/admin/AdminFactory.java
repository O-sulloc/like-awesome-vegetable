package com.i5e2.likeawesomevegetable.user.admin;

import com.i5e2.likeawesomevegetable.payment.order.AdminPaymentOrder;
import com.i5e2.likeawesomevegetable.payment.order.dto.AdminPaymentOrderRequest;
import com.i5e2.likeawesomevegetable.payment.order.dto.AdminPaymentOrderResponse;
import com.i5e2.likeawesomevegetable.user.admin.dto.AdminJoinRequest;
import com.i5e2.likeawesomevegetable.user.admin.dto.AdminJoinResponse;
import com.i5e2.likeawesomevegetable.user.admin.dto.AdminLoginResponse;
import com.i5e2.likeawesomevegetable.user.basic.User;
import com.i5e2.likeawesomevegetable.user.basic.dto.UserType;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
public class AdminFactory {
    public static AdminUser createAdminUser(AdminJoinRequest adminJoinRequest, String encodePassword) {
        return AdminUser.builder()
                .adminType(UserType.ROLE_ADMIN)
                .adminName(adminJoinRequest.getAdminName())
                .adminPhoneNo(adminJoinRequest.getAdminPhoneNo())
                .adminEmail(adminJoinRequest.getAdminEmail())
                .adminNickname(adminJoinRequest.getAdminNickname())
                .adminPassword(encodePassword)
                .build();
    }

    public static User createUser(AdminUser adminUser) {
        return User.builder()
                .adminUser(adminUser)
                .password(adminUser.getAdminPassword())
                .email(adminUser.getAdminEmail())
                .userType(UserType.ROLE_ADMIN)
                .managerName(adminUser.getAdminName())
                .manaverPhoneNo(adminUser.getAdminPhoneNo())
                .build();
    }

    public static AdminJoinResponse from(AdminUser adminUser) {
        return AdminJoinResponse.builder()
                .adminId(adminUser.getId())
                .adminType(adminUser.getAdminType())
                .adminName(adminUser.getAdminName())
                .adminEmail(adminUser.getAdminEmail())
                .adminRegisteredAt(adminUser.getAdminRegisteredAt())
                .build();
    }

    public static AdminPaymentOrder createAdminPaymentOrder(AdminUser adminUser, AdminPaymentOrderRequest adminPaymentOrderRequest) {
        return AdminPaymentOrder.builder()
                .adminUser(adminUser)
                .adminOrderId(makeAdminTransferOrderId())
                .adminOrderInfo(adminPaymentOrderRequest.getAdminOrderInfo())
                .adminTransferAmount(calculateTransferAmount(adminPaymentOrderRequest.getTransferTotalAmount()))
                .transferUserEmail(adminPaymentOrderRequest.getTransferUserEmail())
                .build();
    }

    public static AdminLoginResponse of(Long adminId, String adminEmail, String jwt) {
        return AdminLoginResponse.builder()
                .adminId(adminId)
                .adminEmail(adminEmail)
                .jwt(jwt)
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
