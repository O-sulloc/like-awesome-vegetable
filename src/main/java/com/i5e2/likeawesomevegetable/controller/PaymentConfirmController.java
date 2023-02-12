package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.payment.api.PaymentConfirmService;
import com.i5e2.likeawesomevegetable.domain.payment.api.PointManagerService;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentCardResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentRefundResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentToCancelResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.dto.PaymentToPointResponse;
import com.i5e2.likeawesomevegetable.domain.payment.api.exception.PaymentErrorResponse;
import com.i5e2.likeawesomevegetable.domain.point.UserPointService;
import com.i5e2.likeawesomevegetable.domain.point.dto.PointEventDetailResponse;
import com.i5e2.likeawesomevegetable.domain.point.dto.UserPointResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@Api("User Point Charge Payment Controller")
@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentConfirmController {
    private final PaymentConfirmService paymentConfirmService;
    private final PointManagerService pointManagerService;
    private final UserPointService userPointService;

    @ApiOperation(value = "결제 요청 성공, API Redirect Url"
            , notes = "결제 요청 금액 일치여부 확인 후 이체 API 호출/ 결제 정보 저장 후 사용자 포인트 및 예치금 상태를 업데이트 한다.")
    @RequestMapping(value = "/success")
    public ResponseEntity<Result> paymentSuccess(@RequestParam("paymentKey") String paymentKey
            , @RequestParam("orderId") String orderId
            , @RequestParam("amount") Long amount) throws IOException, InterruptedException {

        paymentConfirmService.verifySuccessRequest(orderId, amount);
        PaymentCardResponse paymentCardResponse = paymentConfirmService.requestFinalPayment(paymentKey, orderId, amount);
        PointEventDetailResponse pointEventDetailResponse = pointManagerService.savePaymentAndPoint(paymentCardResponse);
        UserPointResponse userPointResponse = userPointService.checkUserPointInfo(pointEventDetailResponse.getPointUserEmail());

        return ResponseEntity
                .ok()
                .body(Result.success(new PaymentToPointResponse(paymentCardResponse, pointEventDetailResponse, userPointResponse)));
    }

    @ApiOperation(value = "전액 환불 요청 성공, API Redirect Url"
            , notes = "환불 요청 금액 일치여부 확인 후 이체 API 호출/ 환불 정보 저장 후 사용자 포인트 및 예치금 상태를 업데이트 한다.")
    @RequestMapping("/refund/success")
    public ResponseEntity<Result> paymentRefund(@RequestParam("paymentKey") String paymentKey
            , @RequestParam("cancelReason") String cancelReason
            , @RequestParam("cancelUserEmail") String cancelUserEmail) throws IOException, InterruptedException {

        //TODO: 하나의 트랜젝션으로 관리해야한다 결제와 포인트 연결이기 때문에
        PaymentRefundResponse paymentRefundResponse = paymentConfirmService.requestRefundPayment(cancelReason, paymentKey);
        PointEventDetailResponse pointCancelDetailResponse = pointManagerService.cancelPaymentAndPoint(paymentRefundResponse);
        UserPointResponse userPointResponse = userPointService.refundPoint(paymentRefundResponse, cancelUserEmail);

        return ResponseEntity
                .ok()
                .body(Result.success(new PaymentToCancelResponse(paymentRefundResponse, pointCancelDetailResponse, userPointResponse)));
    }

    @ApiOperation(value = "결제 요청 실패, API Redirect Url"
            , notes = "결제 요청 실패시 에러를 반환한다.")
    @RequestMapping("/fail")
    public Result transferFail(@Valid PaymentErrorResponse paymentErrorResponse) {
        return Result.error(paymentErrorResponse);
    }
}
