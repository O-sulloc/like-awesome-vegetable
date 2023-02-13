package com.i5e2.likeawesomevegetable.payment.api.controller;

import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.payment.api.dto.AdminTransferResponse;
import com.i5e2.likeawesomevegetable.payment.api.dto.PaymentErrorResponse;
import com.i5e2.likeawesomevegetable.payment.api.dto.TransferEventDetailResponse;
import com.i5e2.likeawesomevegetable.payment.api.service.AdminConfirmService;
import com.i5e2.likeawesomevegetable.payment.deposit.dto.DepositToTransferResponse;
import com.i5e2.likeawesomevegetable.payment.deposit.dto.DepositTransferResponse;
import com.i5e2.likeawesomevegetable.payment.deposit.service.DepositApiService;
import com.i5e2.likeawesomevegetable.payment.order.dto.AdminPaymentOrderRequest;
import com.i5e2.likeawesomevegetable.payment.order.dto.AdminPaymentOrderResponse;
import com.i5e2.likeawesomevegetable.payment.order.service.TransferManagerService;
import com.i5e2.likeawesomevegetable.payment.point.dto.UserPointResponse;
import com.i5e2.likeawesomevegetable.payment.point.service.UserPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Api("Admin Transfer Deposit Controller")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminConfirmController {

    private final AdminConfirmService adminConfirmService;
    private final TransferManagerService transferManagerService;
    private final UserPointService userPointService;
    private final DepositApiService depositApiService;

    @ApiOperation(value = "관리자 정산요청 내역"
            , notes = "전자계약 체결 후 관리자 정산 요청 데이터를 저장한다.")
    @PostMapping("/transfer-order")
    private ResponseEntity<Result> createAdminPaymentOrder(@RequestBody @Valid AdminPaymentOrderRequest adminPaymentOrderRequest, Authentication authentication) {
        log.info("user:{}", authentication.getName());
        Result<AdminPaymentOrderResponse> adminTransferOrder = adminConfirmService.createAdminTransferOrder(adminPaymentOrderRequest, authentication.getName());
        return ResponseEntity.ok().body(adminTransferOrder);
    }

    //TODO: 하나의 트랜젝션으로 관리
    @ApiOperation(value = "정산 요청 성공, API Redirect Url"
            , notes = "정산 요청 금액 일치여부 확인 후 이체 API 호출/ 사용자 포인트 및 예치금 상태를 업데이트 한다.")
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


    @ApiOperation(value = "정산 요청 실패, API Redirect Url"
            , notes = "정산 요청 실패시 에러를 반환한다.")
    @GetMapping("/fail")
    public Result transferFail(@Valid PaymentErrorResponse paymentErrorResponse) {
        return Result.error(paymentErrorResponse);
    }
}
