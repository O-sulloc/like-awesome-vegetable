package com.i5e2.likeawesomevegetable.domain.message.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MessageErrorResponse {
    private MessageErrorCode errorCode;
    private String contents;
}
