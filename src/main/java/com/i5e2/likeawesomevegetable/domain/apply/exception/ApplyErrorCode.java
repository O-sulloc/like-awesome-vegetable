package com.i5e2.likeawesomevegetable.domain.apply.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApplyErrorCode {
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "본인인증에 실패하였습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 사용자입니다."),
    NOT_FARM_USER(HttpStatus.UNAUTHORIZED, "농가 사용자가 아닙니다."),
    NOT_COMPANY_USER(HttpStatus.UNAUTHORIZED, "농가 사용자가 아닙니다."),
    PHONE_DISCORD(HttpStatus.UNAUTHORIZED, "사용자의 휴대폰 번호와 불일치합니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "사용자 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}