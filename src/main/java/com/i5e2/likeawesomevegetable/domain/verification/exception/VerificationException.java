package com.i5e2.likeawesomevegetable.domain.verification.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerificationException extends RuntimeException {
    private VerificationErrorCode errorCode;
    private String message;
}
