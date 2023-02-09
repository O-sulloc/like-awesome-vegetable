package com.i5e2.likeawesomevegetable.domain.market.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글 입니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "사용자가 권한이 없습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러");

    private HttpStatus status;
    private String message;
}
