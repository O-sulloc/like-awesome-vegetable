package com.i5e2.likeawesomevegetable.domain.payment.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PaymentErrorCode {
    INVOICE_AMOUNT_MISMATCH(HttpStatus.NOT_FOUND, "인보이스 금액, 요청 금액이 불일치 합니다."),
    NO_PAYMENT_ORDER_RESULT(HttpStatus.NOT_FOUND, "사용자 결제 요청 정보가 존재하지 않습니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "사용자가 권한이 없습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러");

    private HttpStatus status;
    private String message;
}
