package com.i5e2.likeawesomevegetable.domain.payment.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRefundResponse {
    private String paymentKey; //결제 키 값
    private String type; //결제 타입 정보 NORMAL(일반 결제), BILLING(자동 결제), BRANDPAY(브랜드페이)
    private String orderId; //주문 ID
    private String orderName; //주문명
    private String method; //결제 수단
    private Long totalAmount; //총 결제 금액
    private Long balanceAmount; //취소할 수 있는 잔고
    private String status; //결제 처리 상태
    private String requestedAt; //결제가 일어난 날짜와 시간
    private String approvedAt; //결제 승인이 일어난 날짜와 시간
    private String lastTransactionKey; //마지막 거래 키 값
    private Long vat; //부가세
    private boolean isPartialCancelable; //부분 취소 가능 여부
    private Cancels[] cancels; //환불 정보
    private Receipt url; //영수증 확인 주소
}
