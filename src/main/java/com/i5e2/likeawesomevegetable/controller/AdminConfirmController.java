package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.admin.AdminConfirmService;
import com.i5e2.likeawesomevegetable.domain.admin.DepositApiService;
import com.i5e2.likeawesomevegetable.domain.admin.TransferManagerService;
import com.i5e2.likeawesomevegetable.domain.admin.dto.*;
import com.i5e2.likeawesomevegetable.domain.point.UserPointService;
import com.i5e2.likeawesomevegetable.domain.point.dto.UserPointResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminConfirmController {

    private final AdminConfirmService adminConfirmService;
    private final TransferManagerService transferManagerService;
    private final UserPointService userPointService;
    private final DepositApiService depositApiService;

    @PostMapping("/transfer-order")
    private ResponseEntity<Result<AdminPaymentOrderResponse>> createAdminPaymentOrder(@RequestBody AdminPaymentOrderRequest adminPaymentOrderRequest) {
        Result<AdminPaymentOrderResponse> adminTransferOrder = adminConfirmService.createAdminTransferOrder(adminPaymentOrderRequest);
        return ResponseEntity.ok().body(adminTransferOrder);
    }

    @GetMapping("/success")
    public ResponseEntity<Result<DepositToTransferResponse>> transferSuccess(@RequestParam("paymentKey") String paymentKey
            , @RequestParam("orderId") String orderId
            , @RequestParam("amount") Long amount) throws IOException, InterruptedException {
        log.info("paymentKey:{}, orderId:{}, amount:{}", paymentKey, orderId, amount);
        adminConfirmService.adminVerifySuccessRequest(orderId, amount);
        AdminTransferResponse adminTransferResponse = adminConfirmService.requestFinalTransfer(paymentKey, orderId, amount);
        TransferEventDetailResponse transferEventDetailResponse = transferManagerService.savePaymentAndTransfer(adminTransferResponse, orderId);

        //사용자 포인트 예치금 업데이트
        UserPointResponse userPointResponse
                = userPointService.updateUserPointInfo(transferEventDetailResponse.getTransferUserId(), transferEventDetailResponse.getTransferEventAmount());

        //예치금 디테일 상태 변경
        DepositTransferResponse depositTransferResponse = depositApiService.updateDepositStatus(userPointResponse.getUserPointId());
        return ResponseEntity
                .ok()
                .body(Result.success(new DepositToTransferResponse(adminTransferResponse, transferEventDetailResponse, userPointResponse, depositTransferResponse)));
    }

    @GetMapping("/fail")
    public String transferFail() {
        return "";
    }
}
