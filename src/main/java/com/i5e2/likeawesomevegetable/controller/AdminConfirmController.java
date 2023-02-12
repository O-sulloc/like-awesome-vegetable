package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.admin.AdminConfirmService;
import com.i5e2.likeawesomevegetable.domain.admin.DepositApiService;
import com.i5e2.likeawesomevegetable.domain.admin.TransferManagerService;
import com.i5e2.likeawesomevegetable.domain.admin.dto.*;
import com.i5e2.likeawesomevegetable.domain.payment.api.exception.PaymentErrorResponse;
import com.i5e2.likeawesomevegetable.domain.point.UserPointService;
import com.i5e2.likeawesomevegetable.domain.point.dto.UserPointResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private ResponseEntity<Result> createAdminPaymentOrder(@RequestBody @Valid AdminPaymentOrderRequest adminPaymentOrderRequest, Authentication authentication) {
        log.info("user:{}", authentication.getName());
        Result<AdminPaymentOrderResponse> adminTransferOrder = adminConfirmService.createAdminTransferOrder(adminPaymentOrderRequest, authentication.getName());
        return ResponseEntity.ok().body(adminTransferOrder);
    }

    //TODO: 하나의 트랜젝션으로 관리
    @GetMapping("/success")
    public ResponseEntity<Result> transferSuccess(@RequestParam("paymentKey") String paymentKey
            , @RequestParam("orderId") String orderId
            , @RequestParam("amount") Long amount
            , Authentication authentication) throws IOException, InterruptedException {

        adminConfirmService.adminVerifySuccessRequest(orderId, amount);
        AdminTransferResponse adminTransferResponse = adminConfirmService.requestFinalTransfer(paymentKey, orderId, amount);
        TransferEventDetailResponse transferEventDetailResponse = transferManagerService.savePaymentAndTransfer(adminTransferResponse, orderId);

        //사용자 포인트 예치금 업데이트
        UserPointResponse userPointResponse
                = userPointService.updateUserPointInfo(transferEventDetailResponse.getTransferUserEmail());

        //예치금 디테일 상태 변경
        DepositTransferResponse depositTransferResponse = depositApiService.updateDepositStatus(userPointResponse.getUserPointId());
        return ResponseEntity
                .ok()
                .body(Result.success(new DepositToTransferResponse(adminTransferResponse, transferEventDetailResponse, userPointResponse, depositTransferResponse)));
    }

    @GetMapping("/fail")
    public Result transferFail(@Valid PaymentErrorResponse paymentErrorResponse) {
        return Result.error(paymentErrorResponse);
    }
}
