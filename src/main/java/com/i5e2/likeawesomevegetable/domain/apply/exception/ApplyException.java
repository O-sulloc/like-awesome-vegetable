package com.i5e2.likeawesomevegetable.domain.apply.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;
}