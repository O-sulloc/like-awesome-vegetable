package com.i5e2.likeawesomevegetable.domain.message.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageException extends RuntimeException {
    private MessageErrorCode errorCode;
    private String message;
}
