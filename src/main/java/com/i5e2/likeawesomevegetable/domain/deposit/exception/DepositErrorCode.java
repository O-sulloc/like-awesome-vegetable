package com.i5e2.likeawesomevegetable.domain.deposit.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DepositErrorCode {
    NO_POINT_DEPOSIT_RESULT(HttpStatus.NOT_FOUND, "사용자의 보증금 정보가 존재하지 않습니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "사용자가 권한이 없습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러");

    private HttpStatus status;
    private String message;
}
