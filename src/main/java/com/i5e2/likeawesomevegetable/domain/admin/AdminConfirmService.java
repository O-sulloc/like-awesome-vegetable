package com.i5e2.likeawesomevegetable.domain.admin;

import com.amazonaws.services.kms.model.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.admin.dto.AdminPaymentOrderRequest;
import com.i5e2.likeawesomevegetable.domain.admin.dto.AdminPaymentOrderResponse;
import com.i5e2.likeawesomevegetable.domain.admin.dto.AdminTransferResponse;
import com.i5e2.likeawesomevegetable.domain.admin.entity.AdminPaymentOrder;
import com.i5e2.likeawesomevegetable.domain.admin.entity.AdminUser;
import com.i5e2.likeawesomevegetable.repository.AdminPaymentOrderJpaRepository;
import com.i5e2.likeawesomevegetable.repository.AdminUserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminConfirmService {

    @Value("${toss.api.testSecretApiKey}")
    private String testSecretApiKey;
    private final ObjectMapper objectMapper;
    private final AdminUserJpaRepository adminUserJpaRepository;
    private final AdminPaymentOrderJpaRepository adminPaymentOrderJpaRepository;

    @Transactional
    public Result<AdminPaymentOrderResponse> createAdminTransferOrder(AdminPaymentOrderRequest adminPaymentOrderRequest) {
        AdminUser getAdmin = getAdminUser(adminPaymentOrderRequest.getAdminId());
        AdminPaymentOrder adminOrderResult = adminPaymentOrderJpaRepository
                .save(AdminFactory.createAdminPaymentOrder(getAdmin, adminPaymentOrderRequest));

        AdminPaymentOrderResponse paymentOrderResponse = AdminFactory.from(adminOrderResult);
        return Result.success(paymentOrderResponse);
    }

    @Transactional(readOnly = true, timeout = 2)
    public void adminVerifySuccessRequest(String orderId, Long requestAmount) {
        adminPaymentOrderJpaRepository.findByAdminOrderId(orderId)
                .filter(order -> order.getAdminTransferAmount().equals(requestAmount))
                .orElseThrow(() -> new NotFoundException("관리자 요청 데이터가 일치하지 않습니다"));
    }

    @Transactional(timeout = 300, rollbackFor = Exception.class)
    public AdminTransferResponse requestFinalTransfer(String paymentKey, String orderId, Long amount) throws IOException, InterruptedException {
        testSecretApiKey = testSecretApiKey + ":";
        String authKey = new String(Base64.getEncoder().encode(testSecretApiKey.getBytes(StandardCharsets.UTF_8)));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tosspayments.com/v1/payments/confirm"))
                .header("Authorization", "Basic " + authKey)
                .header("Content-Type", "application/json")
                .method("POST"
                        , HttpRequest
                                .BodyPublishers
                                .ofString("{\"paymentKey\":\"" + paymentKey + "\",\"amount\":\"" + amount + "\",\"orderId\":\"" + orderId + "\"}")
                ).build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        log.info("response:{}", response.body());
        return objectMapper.readValue(response.body(), AdminTransferResponse.class);
    }

    private AdminUser getAdminUser(Long adminId) {
        return adminUserJpaRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException("요청한 관리자가 존재하지 않습니다"));
    }

}
