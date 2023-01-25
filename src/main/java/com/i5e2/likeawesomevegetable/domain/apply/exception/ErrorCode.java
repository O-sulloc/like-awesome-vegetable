package com.i5e2.likeawesomevegetable.domain.apply.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "본인인증에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}