package com.i5e2.likeawesomevegetable.domain.point.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PointErrorCode {
    NO_POINT_RESULT(HttpStatus.NOT_FOUND, "사용자 포인트 정보가 존재하지 않습니다."),
    EMPTY_POINT_RESULT(HttpStatus.NO_CONTENT, "사용자 포인트 잔액이 비어있습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에러");

    private HttpStatus status;
    private String message;
}
