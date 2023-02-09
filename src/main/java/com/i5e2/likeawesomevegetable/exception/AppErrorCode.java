package com.i5e2.likeawesomevegetable.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AppErrorCode {

    INVOICE_AMOUNT_MISMATCH(HttpStatus.NOT_FOUND, "인보이스 금액, 요청 금액이 불일치 합니다."),
    NO_PAYMENT_ORDER_RESULT(HttpStatus.NOT_FOUND, "사용자 결제 요청 정보가 존재하지 않습니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "사용자가 권한이 없습니다."),

    NO_POINT_RESULT(HttpStatus.NOT_FOUND, "사용자 포인트 정보가 존재하지 않습니다."),
    EMPTY_POINT_RESULT(HttpStatus.NO_CONTENT, "사용자 포인트 잔액이 비어있습니다."),
    REFUND_AMOUNT_ERROR(HttpStatus.FORBIDDEN, "환불 요청 금액을 확인해주세요."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러"),

    NO_POINT_DEPOSIT_RESULT(HttpStatus.NOT_FOUND, "사용자의 보증금 정보가 존재하지 않습니다.");

    private HttpStatus status;
    private String message;

}
