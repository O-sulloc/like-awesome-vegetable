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

    NO_POINT_DEPOSIT_RESULT(HttpStatus.NOT_FOUND, "사용자의 보증금 정보가 존재하지 않습니다."),

    // Verification ErrorCode
    INVALID_URL(HttpStatus.INTERNAL_SERVER_ERROR, "유효하지 않은 URL입니다."),
    VERIFICATION_DISABLE(HttpStatus.NOT_FOUND, "인증을 수행할 수 없습니다. 다시 신청해주세요."),

    // 기업/농가 파일 업로드 ErrorCode
    FARM_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 농가 회원의 이미지를 찾을 수 없습니다."),
    FARM_FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 농가 회원의 파일을 찾을 수 없습니다."),
    COMPANY_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 기업 회원의 이미지를 찾을 수 없습니다."),
    COMPANY_FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 기업 회원의 파일을 찾을 수 없습니다."),
    FILE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "보낼 파일이 비어있습니다."),
    FILE_SIZE_EXCEED(HttpStatus.BAD_REQUEST, "파일 업로드 용량을 초과했습니다."),
    COMPANY_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 기업 정회원은 없습니다."),
    FARM_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 농가 정회원은 없습니다."),
    LOGIN_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 Email로 회원가입 된 회원은 없습니다."),

    // Message ErrorCode
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 번호의 쪽지가 없습니다."),
    GET_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "수신한 쪽지가 없습니다."),
    SEND_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "송신한 쪽지가 없습니다."),
    INVALID_GETTER(HttpStatus.CONFLICT, "수신 대상이 올바르지 않습니다.");

    private HttpStatus status;
    private String message;

}
