package com.i5e2.likeawesomevegetable.domain.message.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MessageErrorCode {
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 번호의 쪽지가 없습니다."),
    GET_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "수신한 쪽지가 없습니다."),
    SEND_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "송신한 쪽지가 없습니다."),
    INVALID_GETTER(HttpStatus.CONFLICT, "수신 대상이 올바르지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
